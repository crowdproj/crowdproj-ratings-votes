package com.crowdproj.vote.biz.workers

import com.crowdproj.vote.common.CwpVoteContext
import com.crowdproj.vote.common.models.CwpVoteState
import com.crowdproj.vote.lib.cor.ICorChainDsl
import com.crowdproj.vote.lib.cor.worker

fun ICorChainDsl<CwpVoteContext>.initStatus(title: String) = worker() {
    this.title = title
    on { state == CwpVoteState.NONE }
    handle { state = CwpVoteState.RUNNING }
}
