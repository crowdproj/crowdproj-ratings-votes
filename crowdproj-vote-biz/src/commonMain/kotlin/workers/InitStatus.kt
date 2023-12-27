package com.crowdproj.vote.biz.workers

import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.worker
import com.crowdproj.vote.common.CwpVoteContext
import com.crowdproj.vote.common.models.CwpVoteState

fun ICorAddExecDsl<CwpVoteContext>.initStatus(title: String) = worker() {
    this.title = title
    on { state == CwpVoteState.NONE }
    handle { state = CwpVoteState.RUNNING }
}
