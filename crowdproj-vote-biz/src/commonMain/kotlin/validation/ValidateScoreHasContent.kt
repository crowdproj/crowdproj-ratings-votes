package com.crowdproj.vote.biz.validation

import com.crowdproj.vote.common.CwpVoteContext
import com.crowdproj.vote.common.helpers.errorValidation
import com.crowdproj.vote.common.helpers.fail
import com.crowdproj.vote.lib.cor.ICorChainDsl
import com.crowdproj.vote.lib.cor.worker

fun ICorChainDsl<CwpVoteContext>.validateScoreHasContent(title: String) = worker {
    this.title = title
    val regExp = Regex("^[0-9]+$")
    on { voteValidating.score.asString().isNotEmpty() && !voteValidating.score.asString().contains(regExp) }
    handle {
        fail(
            errorValidation(
                field = "score",
                violationCode = "noContent",
                description = "field must contain numbers"
            )
        )
    }
}
