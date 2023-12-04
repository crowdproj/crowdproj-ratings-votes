package com.crowdproj.vote.biz.validation

import com.crowdproj.vote.common.CwpVoteContext
import com.crowdproj.vote.common.models.CwpVoteState
import com.crowdproj.vote.lib.cor.ICorChainDsl
import com.crowdproj.vote.lib.cor.worker

fun ICorChainDsl<CwpVoteContext>.finishVoteValidation(title: String) = worker {
    this.title = title
    on { state == CwpVoteState.RUNNING }
    handle {
        voteValidated = voteValidating
    }
}
