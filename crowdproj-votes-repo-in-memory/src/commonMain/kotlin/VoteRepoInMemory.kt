package com.crowdproj.vote.repoinmemory

import com.benasher44.uuid.uuid4
import com.crowdproj.vote.common.helpers.errorRepoConcurrency
import com.crowdproj.vote.common.models.*
import com.crowdproj.vote.common.repo.DbVoteIdRequest
import com.crowdproj.vote.common.repo.DbVoteRequest
import com.crowdproj.vote.common.repo.DbVoteResponse
import com.crowdproj.vote.common.repo.IVoteRepository
import com.crowdproj.vote.repoinmemory.model.VoteEntity
import io.github.reactivecircus.cache4k.Cache
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

class VoteRepoInMemory(
    initObjects: Collection<CwpVote> = emptyList(),
    ttl: Duration = 2.minutes,
    val randomUuid: () -> String = { uuid4().toString() },
) : IVoteRepository {
    private val mutex: Mutex = Mutex()
    private val cache = Cache.Builder<String, VoteEntity>()
        .expireAfterWrite(ttl)
        .build()

    init {
        initObjects.forEach {
            save(it)
        }
    }

    private fun save(ad: CwpVote) {
        val entity = VoteEntity(ad)
        if (entity.id == null) {
            return
        }
        cache.put(entity.id!!, entity)
    }

    override suspend fun createVote(rq: DbVoteRequest): DbVoteResponse {
        val key = randomUuid()
        val vote = rq.vote.copy(id = CwpVoteId(key), lock = CwpVoteLock(randomUuid()))
        val entity = VoteEntity(vote)
        cache.put(key, entity)
        return DbVoteResponse(
            data = vote,
            isSuccess = true,
        )
    }

    override suspend fun readVote(rq: DbVoteIdRequest): DbVoteResponse {
        val key = rq.id.takeIf { it != CwpVoteId.NONE }?.asString() ?: return resultErrorEmptyId
        return cache.get(key)
            ?.let {
                DbVoteResponse(
                    data = it.toInternal(),
                    isSuccess = true,
                )
            } ?: resultErrorNotFound
    }

    override suspend fun updateVote(rq: DbVoteRequest): DbVoteResponse {
        val key = rq.vote.id.takeIf { it != CwpVoteId.NONE }?.asString() ?: return resultErrorEmptyId
        val oldLock = rq.vote.lock.takeIf { it != CwpVoteLock.NONE }?.asString() ?: return resultErrorEmptyLock
        val newVote = rq.vote.copy(lock = CwpVoteLock(randomUuid()))
        val entity = VoteEntity(newVote)
        return mutex.withLock {
            val oldVote = cache.get(key)
            when {
                oldVote == null -> resultErrorNotFound
                oldVote.lock != oldLock -> DbVoteResponse(
                    data = oldVote.toInternal(),
                    isSuccess = false,
                    errors = listOf(errorRepoConcurrency(CwpVoteLock(oldLock), oldVote.lock?.let { CwpVoteLock(it) }))
                )

                else -> {
                    cache.put(key, entity)
                    DbVoteResponse(
                        data = newVote,
                        isSuccess = true,
                    )
                }
            }
        }
    }

    override suspend fun deleteVote(rq: DbVoteIdRequest): DbVoteResponse {
        val key = rq.id.takeIf { it != CwpVoteId.NONE }?.asString() ?: return resultErrorEmptyId
        val oldLock = rq.lock.takeIf { it != CwpVoteLock.NONE }?.asString() ?: return resultErrorEmptyLock
        val newVoteEntity: VoteEntity = cache.get(key)?.copy(isAccepted = 0) ?: return resultErrorNotFound
        return mutex.withLock {
            val oldVote = cache.get(key)
            when {
                oldVote == null -> resultErrorNotFound
                oldVote.lock != oldLock -> DbVoteResponse(
                    data = oldVote.toInternal(),
                    isSuccess = false,
                    errors = listOf(errorRepoConcurrency(CwpVoteLock(oldLock), oldVote.lock?.let { CwpVoteLock(it) }))
                )

                else -> {
                    cache.put(key, newVoteEntity)
                    DbVoteResponse(
                        data = newVoteEntity.toInternal(),
                        isSuccess = true,
                    )
                }
            }
        }
    }

    companion object {
        val resultErrorEmptyId = DbVoteResponse(
            data = null,
            isSuccess = false,
            errors = listOf(
                CwpVoteError(
                    code = "id-empty",
                    group = "validation",
                    field = "id",
                    message = "Id must not be null or blank"
                )
            )
        )
        val resultErrorNotFound = DbVoteResponse(
            isSuccess = false,
            data = null,
            errors = listOf(
                CwpVoteError(
                    code = "not-found",
                    field = "id",
                    message = "Not Found"
                )
            )
        )
        val resultErrorEmptyLock = DbVoteResponse(
            data = null,
            isSuccess = false,
            errors = listOf(
                CwpVoteError(
                    code = "lock-empty",
                    group = "validation",
                    field = "lock",
                    message = "Lock must not be null or blank"
                )
            )
        )
    }
}
