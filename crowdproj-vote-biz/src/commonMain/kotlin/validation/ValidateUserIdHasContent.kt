package com.crowdproj.vote.biz.validation

import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.worker
import com.crowdproj.vote.common.CwpVoteContext
import com.crowdproj.vote.common.helpers.errorValidation
import com.crowdproj.vote.common.helpers.fail

fun ICorAddExecDsl<CwpVoteContext>.validateUserIdHasContent(title: String) = worker {
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
