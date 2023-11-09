package com.crowdproj.vote.biz.validation

import com.crowdproj.vote.common.CwpVoteContext
import com.crowdproj.vote.common.helpers.errorValidation
import com.crowdproj.vote.common.helpers.fail
import com.crowdproj.vote.common.models.CwpVoteComment
import com.crowdproj.vote.common.models.CwpVoteId
import com.crowdproj.vote.lib.cor.ICorChainDsl
import com.crowdproj.vote.lib.cor.worker

fun ICorChainDsl<CwpVoteContext>.validateCommentIdProperFormat(title: String) = worker {
    this.title = title

    // Может быть вынесен в MkplAdId для реализации различных форматов
    val regExp = Regex("^[0-9a-zA-Z-]+$")
    on { voteValidating.comment != CwpVoteComment.NONE && !voteValidating.comment.id.matches(regExp) }
    handle {
        val encodedId = voteValidating.id.asString()
            .replace("<", "&lt;")
            .replace(">", "&gt;")
        fail(
            errorValidation(
                field = "commentId",
                violationCode = "badFormat",
                description = "value $encodedId must contain only letters and numbers"
            )
        )
    }
}
