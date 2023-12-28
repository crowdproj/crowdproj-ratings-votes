package com.crowdproj.vote.biz.repo

import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.worker
import com.crowdproj.vote.common.CwpVoteContext
import com.crowdproj.vote.common.models.CwpVoteState

fun ICorAddExecDsl<CwpVoteContext>.repoPrepareDelete(title: String) = worker {
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
