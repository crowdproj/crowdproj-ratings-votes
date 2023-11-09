package com.crowdproj.vote.biz.validation

import com.crowdproj.vote.common.CwpVoteContext
import com.crowdproj.vote.common.helpers.errorValidation
import com.crowdproj.vote.common.helpers.fail
import com.crowdproj.vote.lib.cor.ICorChainDsl
import com.crowdproj.vote.lib.cor.worker

// TODO-validation-7: пример обработки ошибки в рамках бизнес-цепочки
fun ICorChainDsl<CwpVoteContext>.validateUserIdHasContent(title: String) = worker {
    this.title = title
    val regExp = Regex("\\p{L}")
    on { voteValidating.userId.asString().isNotEmpty() && !voteValidating.userId.asString().contains(regExp) }
    handle {
        fail(
            errorValidation(
                field = "userId",
                violationCode = "noContent",
                description = "field must contain letters"
            )
        )
    }
}
