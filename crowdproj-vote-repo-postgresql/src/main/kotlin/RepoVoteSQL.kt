package com.crowdproj.vote.repo.postgresql

import com.benasher44.uuid.uuid4
import com.crowdproj.vote.common.helpers.errorRepoConcurrency
import com.crowdproj.vote.common.models.CwpVote
import com.crowdproj.vote.common.models.CwpVoteId
import com.crowdproj.vote.common.models.CwpVoteIsAccepted
import com.crowdproj.vote.common.models.CwpVoteLock
import com.crowdproj.vote.common.repo.DbVoteIdRequest
import com.crowdproj.vote.common.repo.DbVoteRequest
import com.crowdproj.vote.common.repo.DbVoteResponse
import com.crowdproj.vote.common.repo.IVoteRepository
import com.crowdproj.vote.repo.postgresql.VoteTable.fromRow
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class RepoVoteSQL(
    properties: SqlProperties,
    initObjects: Collection<CwpVote> = emptyList(),
    private val randomUuid: () -> String = { uuid4().toString() },
) : IVoteRepository {

    init {
        val driver = when {
            properties.url.startsWith("jdbc:postgresql://") -> "org.postgresql.Driver"
            else -> throw IllegalArgumentException("Unknown driver for url ${properties.url} ")
        }

        Database.connect(
            properties.url,
            driver,
            properties.user,
            properties.password
        )

//        transaction {
//            SchemaUtils.drop(VoteTable)
//            SchemaUtils.create(VoteTable)
//        }
    }

    override suspend fun createVote(rq: DbVoteRequest): DbVoteResponse = transaction {
        val result: CwpVote =
            (
                VoteTable.insert { toRow(it, rq.vote.copy(isAccepted = CwpVoteIsAccepted(true)), randomUuid) }
                    .resultedValues?.singleOrNull()?.let { fromRow(it) }
                    ?: return@transaction DbVoteResponse.errorSave
                ) as CwpVote
        DbVoteResponse.success(result)
    }

    override suspend fun readVote(rq: DbVoteIdRequest): DbVoteResponse = transaction {
        val result: CwpVote =
            VoteTable.select { VoteTable.id eq rq.id.asString() }
                .singleOrNull()?.let { fromRow(it) } ?: return@transaction DbVoteResponse.errorNotFound
        DbVoteResponse.success(result)
    }

    override suspend fun updateVote(rq: DbVoteRequest): DbVoteResponse = transaction {
        val id = rq.vote.id
        if (id == CwpVoteId.NONE) return@transaction DbVoteResponse.errorEmptyId
        var vote = rq.vote
        val oldVote = VoteTable.select { VoteTable.id eq id.asString() }
            .singleOrNull()?.let { fromRow(it) } ?: return@transaction DbVoteResponse.errorNotFound
        if (vote.lock != oldVote.lock) return@transaction DbVoteResponse.error(errorRepoConcurrency(oldVote.lock, vote.lock))
        vote = vote.copy(lock = CwpVoteLock(randomUuid()))
        val result: Boolean =
            VoteTable.update({ VoteTable.id eq (id.asString()) }) { toRow(it, vote, randomUuid) } > 0
        DbVoteResponse(vote, result)
    }

    override suspend fun deleteVote(rq: DbVoteIdRequest): DbVoteResponse = transaction {
        val vote = runBlocking { readVote(rq).data } ?: return@transaction DbVoteResponse.errorEmptyId
        if (vote.lock != rq.lock) return@transaction DbVoteResponse.error(errorRepoConcurrency(vote.lock, rq.lock))
        val newVote = vote.copy(lock = CwpVoteLock(randomUuid()), isAccepted = CwpVoteIsAccepted(false))
        val result: Boolean =
            VoteTable.update({ VoteTable.id eq vote.id.asString() }) {
                it[isAccepted] = 0
                it[lock] = newVote.lock.asString()
            } > 0
        DbVoteResponse.success(newVote, result)
    }
}
