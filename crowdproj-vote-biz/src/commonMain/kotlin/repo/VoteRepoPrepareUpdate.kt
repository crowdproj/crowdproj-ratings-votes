package com.crowdproj.vote.biz.repo

import com.crowdproj.vote.common.CwpVoteContext
import com.crowdproj.vote.common.models.CwpVoteState
import com.crowdproj.vote.lib.cor.ICorChainDsl
import com.crowdproj.vote.lib.cor.worker

fun ICorChainDsl<CwpVoteContext>.repoPrepareUpdate(title: String) = worker {
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

