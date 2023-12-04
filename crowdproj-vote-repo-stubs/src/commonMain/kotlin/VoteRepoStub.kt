package com.crowdproj.vote.repo.stubs

import com.crowdproj.vote.common.repo.DbVoteIdRequest
import com.crowdproj.vote.common.repo.DbVoteRequest
import com.crowdproj.vote.common.repo.DbVoteResponse
import com.crowdproj.vote.common.repo.IVoteRepository
import com.crowdproj.vote.stubs.CwpVoteStub

class VoteRepoStub() : IVoteRepository {
    override suspend fun createVote(rq: DbVoteRequest): DbVoteResponse {
        return DbVoteResponse(
            data = CwpVoteStub.prepareResult { },
            isSuccess = true,
        )
    }

    override suspend fun readVote(rq: DbVoteIdRequest): DbVoteResponse {
        return DbVoteResponse(
            data = CwpVoteStub.prepareResult { },
            isSuccess = true,
        )
    }

    override suspend fun updateVote(rq: DbVoteRequest): DbVoteResponse {
        return DbVoteResponse(
            data = CwpVoteStub.prepareResult { },
            isSuccess = true,
        )
    }

    override suspend fun deleteVote(rq: DbVoteIdRequest): DbVoteResponse {
        return DbVoteResponse(
            data = CwpVoteStub.prepareResult { },
            isSuccess = true,
        )
    }
}
