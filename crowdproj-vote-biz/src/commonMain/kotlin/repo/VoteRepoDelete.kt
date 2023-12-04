package com.crowdproj.vote.biz.repo

import com.crowdproj.vote.lib.cor.ICorChainDsl
import com.crowdproj.vote.common.CwpVoteContext
import com.crowdproj.vote.common.models.CwpVoteState
import com.crowdproj.vote.common.repo.DbVoteIdRequest
import com.crowdproj.vote.lib.cor.worker

fun ICorChainDsl<CwpVoteContext>.repoDelete(title: String) = worker {
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
