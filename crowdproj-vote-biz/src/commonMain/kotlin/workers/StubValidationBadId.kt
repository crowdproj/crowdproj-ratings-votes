package com.crowdproj.vote.biz.workers

import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.worker
import com.crowdproj.vote.common.CwpVoteContext
import com.crowdproj.vote.common.models.CwpVoteError
import com.crowdproj.vote.common.models.CwpVoteState
import com.crowdproj.vote.common.stubs.CwpVoteStubs

fun ICorAddExecDsl<CwpVoteContext>.stubValidationBadId(title: String) = worker {
    this.title = title
    on { stubCase == CwpVoteStubs.BAD_ID && state == CwpVoteState.RUNNING }
    handle {
        state = CwpVoteState.FAILING
        this.errors.add(
            CwpVoteError(
                group = "validation",
                code = "validation-id",
                field = "id",
                message = "Wrong id field"
            )
        )
    }
}
