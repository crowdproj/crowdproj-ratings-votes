package com.crowdproj.vote.biz.general

import com.crowdproj.vote.common.CwpVoteContext
import com.crowdproj.vote.common.models.CwpVoteCommand
import com.crowdproj.vote.common.models.CwpVoteState
import com.crowdproj.vote.lib.cor.ICorChainDsl
import com.crowdproj.vote.lib.cor.chain

fun ICorChainDsl<CwpVoteContext>.operation(
    title: String,
    command: CwpVoteCommand,
    block: ICorChainDsl<CwpVoteContext>.() -> Unit
) = chain {
    block()
    this.title = title
    on { this.command == command && state == CwpVoteState.RUNNING }
}
