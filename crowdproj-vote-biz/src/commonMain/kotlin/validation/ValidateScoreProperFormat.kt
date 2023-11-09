package validation

import com.crowdproj.vote.common.CwpVoteContext
import com.crowdproj.vote.common.helpers.errorValidation
import com.crowdproj.vote.common.helpers.fail
import com.crowdproj.vote.lib.cor.ICorChainDsl
import com.crowdproj.vote.lib.cor.worker

fun ICorChainDsl<CwpVoteContext>.validateScoreProperFormat(title: String) = worker {
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
