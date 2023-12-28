package com.crowdproj.vote.biz.repo

import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.worker
import com.crowdproj.vote.common.CwpVoteContext
import com.crowdproj.vote.common.models.CwpVoteState
import com.crowdproj.vote.common.repo.DbVoteIdRequest

fun ICorAddExecDsl<CwpVoteContext>.repoDelete(title: String) = worker {
    this.title = title
    description = "Cancel vote by Id"
    on { state == CwpVoteState.RUNNING }
    handle {
        val request = DbVoteIdRequest(voteRepoPrepare)
        val result = voteRepo.deleteVote(request)
        if (result.isSuccess && result.data != null) {
            voteRepoDone = result.data!!
        } else {
            state = CwpVoteState.FAILING
            errors.addAll(result.errors)
            voteRepoDone
        }
    }
}
