package com.crowdproj.vote.biz.general

import com.crowdproj.vote.common.CwpVoteContext
import com.crowdproj.vote.common.models.CwpVoteState
import com.crowdproj.vote.common.models.CwpVoteWorkMode
import com.crowdproj.vote.lib.cor.ICorChainDsl
import com.crowdproj.vote.lib.cor.worker

fun ICorChainDsl<CwpVoteContext>.prepareResult(title: String) = worker {
    this.title = title
    description = "Подготовка данных для ответа клиенту на запрос"
    on { workMode != CwpVoteWorkMode.STUB }
    handle {
        state = when (val st = state) {
            CwpVoteState.RUNNING -> CwpVoteState.FINISHING
            else -> st
        }
    }
}
