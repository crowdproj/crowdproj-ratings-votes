package com.crowdproj.vote.repo.tests

import com.crowdproj.vote.common.repo.*

class VoteRepositoryMock(
    private val invokeCreateVote: (DbVoteRequest) -> DbVoteResponse = { DbVoteResponse.MOCK_SUCCESS_EMPTY },
    private val invokeReadVote: (DbVoteIdRequest) -> DbVoteResponse = { DbVoteResponse.MOCK_SUCCESS_EMPTY },
    private val invokeUpdateVote: (DbVoteRequest) -> DbVoteResponse = { DbVoteResponse.MOCK_SUCCESS_EMPTY },
    private val invokeDeleteVote: (DbVoteIdRequest) -> DbVoteResponse = { DbVoteResponse.MOCK_SUCCESS_EMPTY },
) : IVoteRepository {
    override suspend fun createVote(rq: DbVoteRequest): DbVoteResponse {
        return invokeCreateVote(rq)
    }

    override suspend fun readVote(rq: DbVoteIdRequest): DbVoteResponse {
        return invokeReadVote(rq)
    }

    override suspend fun updateVote(rq: DbVoteRequest): DbVoteResponse {
        return invokeUpdateVote(rq)
    }

    override suspend fun deleteVote(rq: DbVoteIdRequest): DbVoteResponse {
        return invokeDeleteVote(rq)
    }
}
