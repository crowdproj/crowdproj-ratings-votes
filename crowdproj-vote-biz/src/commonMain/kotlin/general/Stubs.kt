package com.crowdproj.vote.biz.general

import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.chain
import com.crowdproj.vote.common.CwpVoteContext
import com.crowdproj.vote.common.models.CwpVoteState
import com.crowdproj.vote.common.models.CwpVoteWorkMode

fun ICorAddExecDsl<CwpVoteContext>.stubs(title: String, block: ICorAddExecDsl<CwpVoteContext>.() -> Unit) = chain {
    block()
    this.title = title
    on { workMode == CwpVoteWorkMode.STUB && state == CwpVoteState.RUNNING }
}
