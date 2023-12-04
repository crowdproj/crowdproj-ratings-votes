package com.crowdproj.vote.repo.postgresql

import com.crowdproj.vote.common.NONE
import com.crowdproj.vote.common.models.*
import kotlinx.datetime.*
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.statements.UpdateBuilder

object VoteTable : Table(name = "votes") {
    val id = varchar("id", 128)
    val lock = varchar("lock", 128)
    val userId = varchar("user_id", 128)
    val ratingId = varchar("rating_id", 128)
    val score = integer("score").nullable()
    val comment = varchar("comment", 250).nullable()
    val isAccepted = integer("is_accepted")
    val createAt = datetime("create_at")
    val updateAt = datetime("update_at").nullable()

    override val primaryKey: PrimaryKey = PrimaryKey(id)

    fun fromRow(result: InsertStatement<Number>): CwpVote =
        CwpVote(
            id = CwpVoteId(result[id].toString()),
            lock = CwpVoteLock(result[lock]),
            userId = CwpVoteUserId(result[userId]),
            ratingId = CwpVoteRatingId(result[ratingId].toString()),
            score = CwpVoteScore(result[score].toString()),
            comment = CwpVoteComment(result[comment] ?: ""),
            isAccepted = CwpVoteIsAccepted(result[isAccepted] == 1),
            createAt = result[createAt].toKotlinLocalDateTime().toInstant(TimeZone.currentSystemDefault()),
            updateAt = result[updateAt]?.toKotlinLocalDateTime()?.toInstant(TimeZone.currentSystemDefault())
                ?: Instant.NONE
        )

    fun fromRow(result: ResultRow): CwpVote =
        CwpVote(
            id = CwpVoteId(result[id].toString()),
            lock = CwpVoteLock(result[lock]),
            userId = CwpVoteUserId(result[userId]),
            ratingId = CwpVoteRatingId(result[ratingId].toString()),
            score = CwpVoteScore(result[score].toString()),
            comment = CwpVoteComment(result[comment] ?: ""),
            isAccepted = CwpVoteIsAccepted(result[isAccepted] == 1),
            createAt = result[createAt].toKotlinLocalDateTime().toInstant(TimeZone.currentSystemDefault()),
            updateAt = result[updateAt]?.toKotlinLocalDateTime()?.toInstant(TimeZone.currentSystemDefault())
                ?: Instant.NONE
        )

    fun toRow(it: UpdateBuilder<*>, vote: CwpVote, randomUuid: () -> String) {
        it[id] = vote.id.takeIf { it != CwpVoteId.NONE }?.asString() ?: randomUuid()
        it[userId] = vote.userId.asString()
        it[ratingId] = vote.ratingId.asString()
        it[comment] = vote.comment.asString()
        it[score] = vote.score.asString().toInt()
        it[isAccepted] = if (vote.isAccepted.value()) 1 else 0
        it[createAt] = (
            vote.createAt.takeIf { it != Instant.NONE }
                ?: Clock.System.now()
            ).toLocalDateTime(TimeZone.currentSystemDefault()).toJavaLocalDateTime()
        it[updateAt] = (
            vote.updateAt.takeIf { it != Instant.NONE }
                ?: Clock.System.now()
            ).toLocalDateTime(TimeZone.currentSystemDefault()).toJavaLocalDateTime()
        it[userId] = vote.userId.asString()
        it[lock] = vote.lock.takeIf { it != CwpVoteLock.NONE }?.asString() ?: randomUuid()
    }
}
