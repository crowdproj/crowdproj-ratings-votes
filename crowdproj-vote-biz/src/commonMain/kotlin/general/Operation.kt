package com.crowdproj.vote.biz.general

import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.chain
import com.crowdproj.vote.common.CwpVoteContext
import com.crowdproj.vote.common.models.CwpVoteCommand
import com.crowdproj.vote.common.models.CwpVoteState

fun ICorAddExecDsl<CwpVoteContext>.operation(
    title: String,
    command: CwpVoteCommand,
    block: ICorAddExecDsl<CwpVoteContext>.() -> Unit
) = chain {
    block()
    this.title = title
    on { this.command == command && state == CwpVoteState.RUNNING }
}
