package com.crowdproj.vote.biz.workers

import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.worker
import com.crowdproj.vote.common.CwpVoteContext
import com.crowdproj.vote.common.models.CwpVoteState
import com.crowdproj.vote.common.stubs.CwpVoteStubs
import com.crowdproj.vote.stubs.CwpVoteStub

fun ICorAddExecDsl<CwpVoteContext>.stubDeleteSuccess(title: String) = worker {
    this.title = title
    on { stubCase == CwpVoteStubs.SUCCESS && state == CwpVoteState.RUNNING }
    handle {
        state = CwpVoteState.FINISHING
        val stub = CwpVoteStub.prepareResult {
            voteRequest.ratingId.takeIf { it.asString().isNotBlank() }?.also { this.ratingId = it }
            voteRequest.userId.takeIf { it.asString().isNotBlank() }?.also { this.userId = it }
            voteRequest.score.takeIf { it.asString().isNotBlank() }?.also { this.score = it }
        }
        voteResponse = stub
    }
}
