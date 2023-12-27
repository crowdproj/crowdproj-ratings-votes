package com.crowdproj.vote.biz.validation

import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.worker
import com.crowdproj.vote.common.CwpVoteContext
import com.crowdproj.vote.common.models.CwpVoteState

fun ICorAddExecDsl<CwpVoteContext>.finishVoteValidation(title: String) = worker {
    this.title = title
    on { state == CwpVoteState.RUNNING }
    handle {
        voteValidated = voteValidating
    }
}
