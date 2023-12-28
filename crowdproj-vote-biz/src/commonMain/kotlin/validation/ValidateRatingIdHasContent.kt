package com.crowdproj.vote.biz.validation

import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.worker
import com.crowdproj.vote.common.CwpVoteContext
import com.crowdproj.vote.common.helpers.errorValidation
import com.crowdproj.vote.common.helpers.fail

fun ICorAddExecDsl<CwpVoteContext>.validateRatingIdHasContent(title: String) = worker {
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
