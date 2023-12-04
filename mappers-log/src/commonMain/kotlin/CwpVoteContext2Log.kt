package com.crowdproj.rating.mappers.log

import com.crowdproj.vote.api.v1.models.CommonLogModel
import com.crowdproj.vote.api.v1.models.CwpLogModel
import com.crowdproj.vote.api.v1.models.ErrorLogModel
import com.crowdproj.vote.api.v1.models.VoteLog
import com.crowdproj.vote.common.CwpVoteContext
import com.crowdproj.vote.common.models.*
import kotlinx.datetime.Clock

fun CwpVoteContext.toLog(logId: String) = CommonLogModel(
    messageTime = Clock.System.now().toString(),
    logId = logId,
    source = "cwp-votes",
    vote = toCwpLog(),
    errors = errors.map { it.toLog() },
)

fun CwpVoteContext.toCwpLog(): CwpLogModel? {
    val ratingNone = CwpVote()
    return CwpLogModel(
        requestId = requestId.takeIf { it != CwpVoteRequestId.NONE }?.asString(),
        requestVote = voteRequest.takeIf { it != ratingNone }?.toLog(),
        responseVote = voteResponse.takeIf { it != ratingNone }?.toLog(),
        responseVotes = votesResponse.takeIf { it.isNotEmpty() }?.filter { it != ratingNone }?.map { it.toLog() },
    ).takeIf { it != CwpLogModel() }
}

fun CwpVoteError.toLog() = ErrorLogModel(
    code = code.takeIf { it.isNotBlank() },
    group = group.takeIf { it.isNotBlank() },
    field = field.takeIf { it.isNotBlank() },
    description = message.takeIf { it.isNotBlank() },
//    level = level.name,
)

fun CwpVote.toLog() = VoteLog(
    id = id.takeIf { it != CwpVoteId.NONE }?.asString(),
    ratingId = ratingId.takeIf { it != CwpVoteRatingId.NONE }?.asString(),
    score = score.takeIf { it != CwpVoteScore.NONE }?.asString(),
    userId = userId.takeIf { it != CwpVoteUserId.NONE }?.asString()
)
