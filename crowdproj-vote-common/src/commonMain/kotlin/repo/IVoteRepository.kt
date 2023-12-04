package com.crowdproj.vote.common.repo

interface IVoteRepository {
    suspend fun createVote(rq: DbVoteRequest): DbVoteResponse
    suspend fun readVote(rq: DbVoteIdRequest): DbVoteResponse
    suspend fun updateVote(rq: DbVoteRequest): DbVoteResponse
    suspend fun deleteVote(rq: DbVoteIdRequest): DbVoteResponse

    companion object {
        val NONE = object : IVoteRepository {
            inner class NoneRepository() : RuntimeException("Repository is not set")

            suspend fun forbidden(): Nothing = throw NoneRepository()
            override suspend fun createVote(rq: DbVoteRequest): DbVoteResponse {
                forbidden()
            }

            override suspend fun readVote(rq: DbVoteIdRequest): DbVoteResponse {
                forbidden()
            }

            override suspend fun updateVote(rq: DbVoteRequest): DbVoteResponse {
                forbidden()
            }

            override suspend fun deleteVote(rq: DbVoteIdRequest): DbVoteResponse {
                forbidden()
            }
        }
    }
}
