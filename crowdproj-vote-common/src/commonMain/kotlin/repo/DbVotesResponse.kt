package com.crowdproj.vote.common.repo

import com.crowdproj.vote.common.models.CwpVote
import com.crowdproj.vote.common.models.CwpVoteError

data class DbVotesResponse(
    override val data: List<CwpVote>?,
    override val isSuccess: Boolean,
    override val errors: List<CwpVoteError> = emptyList(),
) : IDbResponse<List<CwpVote>> {

    companion object {
        val MOCK_SUCCESS_EMPTY = DbVotesResponse(emptyList(), true)
        fun success(result: List<CwpVote>) = DbVotesResponse(result, true)
        fun error(errors: List<CwpVoteError>) = DbVotesResponse(null, false, errors)
        fun error(error: CwpVoteError) = DbVotesResponse(null, false, listOf(error))
    }
}
