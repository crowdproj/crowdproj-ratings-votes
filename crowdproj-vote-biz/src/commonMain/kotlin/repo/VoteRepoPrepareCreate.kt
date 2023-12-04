package com.crowdproj.vote.biz.repo

import com.crowdproj.vote.common.CwpVoteContext
import com.crowdproj.vote.common.models.CwpVoteState
import com.crowdproj.vote.lib.cor.ICorChainDsl
import com.crowdproj.vote.lib.cor.worker

fun ICorChainDsl<CwpVoteContext>.repoPrepareCreate(title: String) = worker {
    this.title = title
    description = "Prepare object to save into database"
    on { state == CwpVoteState.RUNNING }
    handle {
        voteRepoRead = voteValidated
        voteRepoPrepare = voteRepoRead
    }
}
