package com.crowdproj.vote.biz.workers

import com.crowdproj.vote.common.CwpVoteContext
import com.crowdproj.vote.common.models.CwpVoteError
import com.crowdproj.vote.common.models.CwpVoteState
import com.crowdproj.vote.common.stubs.CwpVoteStubs
import com.crowdproj.vote.lib.cor.ICorChainDsl
import com.crowdproj.vote.lib.cor.worker

fun ICorChainDsl<CwpVoteContext>.stubValidationBadScore(title: String) = worker {
    this.title = title
    on { stubCase == CwpVoteStubs.BAD_SCORE && state == CwpVoteState.RUNNING }
    handle {
        state = CwpVoteState.FAILING
        this.errors.add(
            CwpVoteError(
                group = "validation",
                code = "validation-score",
                field = "score",
                message = "Wrong score field"
            )
        )
    }
}
