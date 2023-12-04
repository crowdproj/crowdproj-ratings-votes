package com.crowdproj.vote.biz.repo

import com.crowdproj.vote.common.CwpVoteContext
import com.crowdproj.vote.common.models.CwpVoteState
import com.crowdproj.vote.lib.cor.ICorChainDsl
import com.crowdproj.vote.lib.cor.worker

fun ICorChainDsl<CwpVoteContext>.repoPrepareDelete(title: String) = worker {
    this.title = title
    description = """
        Prepare data to cancel vote
    """.trimIndent()
    on { state == CwpVoteState.RUNNING }
    handle {
        voteRepoPrepare = voteRepoRead.copy(
            lock = voteValidated.lock
        )
    }
}
