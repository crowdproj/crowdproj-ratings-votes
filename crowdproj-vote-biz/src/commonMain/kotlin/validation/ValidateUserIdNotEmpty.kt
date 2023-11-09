package com.crowdproj.vote.biz.validation

import com.crowdproj.vote.common.CwpVoteContext
import com.crowdproj.vote.common.helpers.errorValidation
import com.crowdproj.vote.common.helpers.fail
import com.crowdproj.vote.lib.cor.ICorChainDsl
import com.crowdproj.vote.lib.cor.worker

// TODO-validation-7: пример обработки ошибки в рамках бизнес-цепочки
fun ICorChainDsl<CwpVoteContext>.validateUserIdNotEmpty(title: String) = worker {
    this.title = title
    on { voteValidating.userId.asString().isEmpty() }
    handle {
        fail(
            errorValidation(
                field = "userId",
                violationCode = "noContent",
                description = "field must not be empty"
            )
        )
    }
}
