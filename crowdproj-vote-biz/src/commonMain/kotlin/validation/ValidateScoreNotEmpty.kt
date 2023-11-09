package com.crowdproj.vote.biz.validation

import com.crowdproj.vote.common.CwpVoteContext
import com.crowdproj.vote.common.helpers.errorValidation
import com.crowdproj.vote.common.helpers.fail
import com.crowdproj.vote.lib.cor.ICorChainDsl
import com.crowdproj.vote.lib.cor.worker

// TODO-validation-4: смотрим пример COR DSL валидации
fun ICorChainDsl<CwpVoteContext>.validateScoreNotEmpty(title: String) = worker {
    this.title = title
    on { voteValidating.score.asString().isEmpty() }
    handle {
        fail(
            errorValidation(
                field = "score",
                violationCode = "empty",
                description = "field must not be empty"
            )
        )
    }
}
