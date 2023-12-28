package com.crowdproj.vote.biz.repo

import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.worker
import com.crowdproj.vote.common.CwpVoteContext
import com.crowdproj.vote.common.models.CwpVoteState
import com.crowdproj.vote.common.repo.DbVoteRequest

fun ICorAddExecDsl<CwpVoteContext>.repoCreate(title: String) = worker {
    this.title = title
    description = "Add vote into a database table"
    on { state == CwpVoteState.RUNNING }
    handle {
        val request = DbVoteRequest(voteRepoPrepare)
        val result = voteRepo.createVote(request)
        val resultVote = result.data
        if (result.isSuccess && resultVote != null) {
            voteRepoDone = resultVote
        } else {
            state = CwpVoteState.FAILING
            errors.addAll(result.errors)
        }
    }
}
