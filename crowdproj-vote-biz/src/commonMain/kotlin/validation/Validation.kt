package com.crowdproj.vote.biz.validation

import com.crowdproj.vote.common.CwpVoteContext
import com.crowdproj.vote.common.models.CwpVoteState
import com.crowdproj.vote.lib.cor.ICorChainDsl
import com.crowdproj.vote.lib.cor.chain

fun ICorChainDsl<CwpVoteContext>.validation(block: ICorChainDsl<CwpVoteContext>.() -> Unit) = chain {
    block()
    title = "Валидация"

    on { state == CwpVoteState.RUNNING }
}
