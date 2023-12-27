package com.crowdproj.vote.biz.repo

import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.worker
import com.crowdproj.vote.common.CwpVoteContext
import com.crowdproj.vote.common.models.CwpVoteState

fun ICorAddExecDsl<CwpVoteContext>.repoPrepareUpdate(title: String) = worker {
    this.title = title
    description = "Prepare data to save into database table"
    on { state == CwpVoteState.RUNNING }
    handle {
        voteRepoPrepare = voteRepoRead.copy(
            score = voteValidated.score,
            comment = voteValidated.comment,
            lock = voteValidated.lock
        )
    }
}

