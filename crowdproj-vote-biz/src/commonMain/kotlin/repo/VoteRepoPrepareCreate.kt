package com.crowdproj.vote.biz.repo

import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.worker
import com.crowdproj.vote.common.CwpVoteContext
import com.crowdproj.vote.common.models.CwpVoteState

fun ICorAddExecDsl<CwpVoteContext>.repoPrepareCreate(title: String) = worker {
    this.title = title
    description = "Prepare object to save into database"
    on { state == CwpVoteState.RUNNING }
    handle {
        voteRepoRead = voteValidated
        voteRepoPrepare = voteRepoRead
    }
}
