package workers

import com.crowdproj.vote.common.CwpVoteContext
import com.crowdproj.vote.common.helpers.fail
import com.crowdproj.vote.common.models.CwpVoteError
import com.crowdproj.vote.common.models.CwpVoteState
import com.crowdproj.vote.lib.cor.ICorChainDsl
import com.crowdproj.vote.lib.cor.worker

fun ICorChainDsl<CwpVoteContext>.stubNoCase(title: String) = worker {
    this.title = title
    on { state == CwpVoteState.RUNNING }
    handle {
        fail(
            CwpVoteError(
                code = "validation",
                field = "stub",
                group = "validation",
                message = "Wrong stub case is requested: ${stubCase.name}"
            )
        )
    }
}
