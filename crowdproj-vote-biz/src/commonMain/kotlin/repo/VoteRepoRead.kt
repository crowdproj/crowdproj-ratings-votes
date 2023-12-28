package com.crowdproj.vote.biz.repo

import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.worker
import com.crowdproj.vote.common.CwpVoteContext
import com.crowdproj.vote.common.models.CwpVoteState
import com.crowdproj.vote.common.repo.DbVoteIdRequest

fun ICorAddExecDsl<CwpVoteContext>.repoRead(title: String) = worker {
    this.title = title
    description = "Read vote from database table"
    on { state == CwpVoteState.RUNNING }
    handle {
        val request = DbVoteIdRequest(voteValidated)
        val result = voteRepo.readVote(request)
        val resultVote = result.data
        if (result.isSuccess && resultVote != null) {
            voteRepoRead = resultVote
        } else {
            state = CwpVoteState.FAILING
            errors.addAll(result.errors)
        }
    }
}
