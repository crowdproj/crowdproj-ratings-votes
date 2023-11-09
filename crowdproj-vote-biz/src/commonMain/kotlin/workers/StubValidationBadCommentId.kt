package com.crowdproj.vote.biz.workers

import com.crowdproj.vote.common.CwpVoteContext
import com.crowdproj.vote.common.models.CwpVoteError
import com.crowdproj.vote.common.models.CwpVoteState
import com.crowdproj.vote.common.stubs.CwpVoteStubs
import com.crowdproj.vote.lib.cor.ICorChainDsl
import com.crowdproj.vote.lib.cor.worker

fun ICorChainDsl<CwpVoteContext>.stubValidationBadCommentId(title: String) = worker {
    this.title = title
    on { stubCase == CwpVoteStubs.BAD_COMMENT_ID && state == CwpVoteState.RUNNING }
    handle {
        state = CwpVoteState.FAILING
        this.errors.add(
            CwpVoteError(
                group = "validation",
                code = "validation-commentId",
                field = "commentId",
                message = "Wrong commentId field"
            )
        )
    }
}
