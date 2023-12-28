package com.crowdproj.vote.repoinmemory.model

import com.crowdproj.vote.common.NONE
import com.crowdproj.vote.common.models.*
import kotlinx.datetime.Instant

data class VoteEntity(
    var id: String? = null,
    var userId: String? = null,
    var ratingId: String? = null,
    var score: String? = null,
    var comment: String? = null,
    var isAccepted: Int? = null,
    var lock: String? = null,
    var createAt: Instant? = null,
    var updateAt: Instant? = null
) {
    constructor(model: CwpVote) : this(
        id = model.id.asString().takeIf { it.isNotBlank() },
        userId = model.userId.asString().takeIf { it.isNotBlank() },
        ratingId = model.ratingId.asString().takeIf { it.isNotBlank() },
        score = model.score.asString().takeIf { it.isNotBlank() },
        comment = model.comment.value.takeIf { it.isNotBlank() },
        isAccepted = model.isAccepted.getInt(),
        createAt = model.createAt.takeIf { it != Instant.NONE },
        updateAt = model.updateAt.takeIf { it != Instant.NONE },
        lock = model.lock.asString().takeIf { it.isNotBlank() }
    )

    fun toInternal() = CwpVote(
        id = id?.let { CwpVoteId(it) } ?: CwpVoteId.NONE,
        userId = userId?.let { CwpVoteUserId(it) } ?: CwpVoteUserId.NONE,
        ratingId = ratingId?.let { CwpVoteRatingId(it) } ?: CwpVoteRatingId.NONE,
        score = score?.let { CwpVoteScore(it) } ?: CwpVoteScore.NONE,
        comment = comment?.let { CwpVoteComment(it) } ?: CwpVoteComment.NONE,
        isAccepted = isAccepted?.let { CwpVoteIsAccepted(it.getBoolean()) } ?: CwpVoteIsAccepted.NONE,
        createAt = createAt ?: Instant.NONE,
        updateAt = updateAt ?: Instant.NONE,
        lock = lock?.let { CwpVoteLock(it) } ?: CwpVoteLock.NONE,
    )
}

private fun Int.getBoolean(): Boolean =
    this == 1

private fun CwpVoteIsAccepted.getInt(): Int? =
    if (this.value()) 1
    else 0
