package validation

import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.worker
import com.crowdproj.vote.common.CwpVoteContext
import com.crowdproj.vote.common.helpers.errorValidation
import com.crowdproj.vote.common.helpers.fail

fun ICorAddExecDsl<CwpVoteContext>.validateScoreProperFormat(title: String) = worker {
    this.title = title

    val regExp = Regex("^[0-9]+$")
    on { !voteValidating.score.asString().matches(regExp) }
    handle {
        fail(
            errorValidation(
                field = "score",
                violationCode = "badFormat",
                description = "value must contain only numbers"
            )
        )
    }
}
