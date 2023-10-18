package com.crowdproj.vote.common.repo

import com.crowdproj.vote.common.models.CwpVote
import com.crowdproj.vote.common.models.CwpVoteError

data class DbVoteResponse(
    override val data: CwpVote?,
    override val isSuccess: Boolean,
    override val errors: List<CwpVoteError> = emptyList()
) : IDbResponse<CwpVote> {

    companion object {
        val MOCK_SUCCESS_EMPTY = DbVoteResponse(null, true)
        fun success(result: CwpVote) = DbVoteResponse(result, true)
        fun error(errors: List<CwpVoteError>) = DbVoteResponse(null, false, errors)
        fun error(error: CwpVoteError) = DbVoteResponse(null, false, listOf(error))
    }
}
