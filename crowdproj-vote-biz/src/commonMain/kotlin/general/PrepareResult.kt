package com.crowdproj.vote.biz.general

import com.crowdproj.vote.common.CwpVoteContext
import com.crowdproj.vote.common.models.CwpVoteState
import com.crowdproj.vote.common.models.CwpVoteWorkMode
import com.crowdproj.vote.lib.cor.ICorChainDsl
import com.crowdproj.vote.lib.cor.worker

fun ICorChainDsl<CwpVoteContext>.prepareResult(title: String) = worker {
    this.title = title
    description = "Prepare object to send response to client"
    on { workMode != CwpVoteWorkMode.STUB }
    handle {
        voteResponse = voteRepoDone
        votesResponse = votesRepoDone
        state = when (val st = state) {
            CwpVoteState.RUNNING -> CwpVoteState.FINISHING
            else -> st
        }
    }
}
