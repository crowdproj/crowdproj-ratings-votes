package com.crowdproj.vote.biz.validation

import com.crowdproj.vote.common.CwpVoteContext
import com.crowdproj.vote.common.helpers.errorValidation
import com.crowdproj.vote.common.helpers.fail
import com.crowdproj.vote.lib.cor.ICorChainDsl
import com.crowdproj.vote.lib.cor.worker

// TODO-validation-7: пример обработки ошибки в рамках бизнес-цепочки
fun ICorChainDsl<CwpVoteContext>.validateRatingIdHasContent(title: String) = worker {
    this.title = title
    val regExp = Regex("^[0-9]+$")
    on { voteValidating.ratingId.asString().isNotEmpty() && !voteValidating.ratingId.asString().contains(regExp) }
    handle {
        fail(
            errorValidation(
                field = "ratingId",
                violationCode = "noContent",
                description = "field must contain numbers"
            )
        )
    }
}
