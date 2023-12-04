package com.crowdproj.vote.common.repo

import com.crowdproj.vote.common.helpers.errorRepoConcurrency
import com.crowdproj.vote.common.models.CwpVote
import com.crowdproj.vote.common.models.CwpVoteError
import com.crowdproj.vote.common.helpers.errorNotFound as cwpErrorNotFound
import com.crowdproj.vote.common.helpers.errorSave as cwpErrorSave
import com.crowdproj.vote.common.helpers.errorEmptyId as cwpErrorEmptyId

data class DbVoteResponse(
    override val data: CwpVote?,
    override val isSuccess: Boolean,
    override val errors: List<CwpVoteError> = emptyList()
) : IDbResponse<CwpVote> {

    companion object {
        val MOCK_SUCCESS_EMPTY = DbVoteResponse(null, true)
        fun success(result: CwpVote, isSuccess: Boolean = true) = DbVoteResponse(result, true)
        fun error(errors: List<CwpVoteError>) = DbVoteResponse(null, false, errors)
        fun error(error: CwpVoteError) = DbVoteResponse(null, false, listOf(error))

        val errorNotFound = error(cwpErrorNotFound)
        val errorSave = error(cwpErrorSave)
        val errorEmptyId = error(cwpErrorEmptyId)
    }
}
