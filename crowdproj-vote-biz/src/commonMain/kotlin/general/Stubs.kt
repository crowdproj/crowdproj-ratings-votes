package com.crowdproj.vote.biz.general

import com.crowdproj.vote.common.CwpVoteContext
import com.crowdproj.vote.common.models.CwpVoteState
import com.crowdproj.vote.common.models.CwpVoteWorkMode
import com.crowdproj.vote.lib.cor.ICorChainDsl
import com.crowdproj.vote.lib.cor.chain

fun ICorChainDsl<CwpVoteContext>.stubs(title: String, block: ICorChainDsl<CwpVoteContext>.() -> Unit) = chain {
    block()
    this.title = title
    on { workMode == CwpVoteWorkMode.STUB && state == CwpVoteState.RUNNING }
}
