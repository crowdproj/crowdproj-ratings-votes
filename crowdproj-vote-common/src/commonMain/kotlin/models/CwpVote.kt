package com.crowdproj.vote.common.models

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

data class CwpVote(
    var id: CwpVoteId = CwpVoteId.NONE,
    var lock: CwpVoteLock = CwpVoteLock.NONE,
    var userId: CwpVoteUserId = CwpVoteUserId.NONE,
    var ratingId: CwpVoteRatingId = CwpVoteRatingId.NONE,
    var score: CwpVoteScore = CwpVoteScore.NONE,
    var comment: CwpVoteComment = CwpVoteComment.NONE,
    var isAccepted: CwpVoteIsAccepted = CwpVoteIsAccepted.NONE,
    var createAt: Instant? = Clock.System.now(),
    var updateAt: Instant? = Clock.System.now()
) {
    fun isEmpty() = this == NONE

    companion object {
        val NONE = CwpVote()
    }
}
