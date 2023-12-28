package com.crowdproj.vote.biz.validation

import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.chain
import com.crowdproj.vote.common.CwpVoteContext
import com.crowdproj.vote.common.models.CwpVoteState

fun ICorAddExecDsl<CwpVoteContext>.validation(block: ICorAddExecDsl<CwpVoteContext>.() -> Unit) = chain {
    block()
    title = "Валидация"

    on { state == CwpVoteState.RUNNING }
}
