package com.crowdproj.vote.biz.validation

import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.worker
import com.crowdproj.vote.common.CwpVoteContext
import com.crowdproj.vote.common.helpers.errorValidation
import com.crowdproj.vote.common.helpers.fail

fun ICorAddExecDsl<CwpVoteContext>.validateRatingIdNotEmpty(title: String) = worker {
    this.title = title
    on { voteValidating.ratingId.asString().isEmpty() }
    handle {
        fail(
            errorValidation(
                field = "ratingId",
                violationCode = "empty",
                description = "field must not be empty"
            )
        )
    }
}
