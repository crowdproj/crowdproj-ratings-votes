package com.crowdproj.vote.app.spring.controller

import com.crowdproj.rating.mappers.log.toLog
import com.crowdproj.vote.api.v1.mappers.fromApi
import com.crowdproj.vote.api.v1.mappers.toApi
import com.crowdproj.vote.api.v1.models.IRequestVote
import com.crowdproj.vote.api.v1.models.IResponseVote
import com.crowdproj.vote.app.spring.CwpVoteBlockingProcessor
import com.crowdproj.vote.common.CwpVoteContext
import com.crowdproj.vote.common.helpers.asCwpVoteError
import com.crowdproj.vote.common.models.CwpVoteCommand
import com.crowdproj.vote.common.models.CwpVoteState
import com.crowdproj.vote.logging.common.ICwpLogWrapper
import kotlinx.datetime.Clock

suspend inline fun <reified Q : IRequestVote, reified R : IResponseVote> processV1(
    processor: CwpVoteBlockingProcessor,
    command: CwpVoteCommand? = null,
    request: Q,
    logger: ICwpLogWrapper,
    logId: String,
): R {
    val ctx = CwpVoteContext(
        timeStart = Clock.System.now(),
    )
    return try {
        logger.doWithLogging(id = logId) {
            ctx.fromApi(request)
            logger.info(
                msg = "$command request is got",
                data = ctx.toLog("$logId-got")
            )
            processor.exec(ctx)
            logger.info(
                msg = "$command request is handled",
                data = ctx.toLog("$logId-handled")
            )
            ctx.toApi() as R
        }
    } catch (e: Throwable) {
        logger.doWithLogging(id = "$logId-failure") {
            command?.also { ctx.command = it }
            logger.error(
                msg = "$command handling failed",
            )
            ctx.state = CwpVoteState.FINISHING
            ctx.errors.add(e.asCwpVoteError())
            processor.exec(ctx)
            ctx.toApi() as R
        }
    }
}
