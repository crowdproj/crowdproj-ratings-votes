package com.crowdproj.vote.common.models

import com.crowdproj.vote.common.NONE
import kotlinx.datetime.Instant

data class CwpVote(
    var id: CwpVoteId = CwpVoteId.NONE,
    var lock: CwpVoteLock = CwpVoteLock.NONE,
    var userId: CwpVoteUserId = CwpVoteUserId.NONE,
    var ratingId: CwpVoteRatingId = CwpVoteRatingId.NONE,
    var score: CwpVoteScore = CwpVoteScore.NONE,
    var comment: CwpVoteComment = CwpVoteComment.NONE,
    var isAccepted: CwpVoteIsAccepted = CwpVoteIsAccepted.NONE,
    var createAt: Instant? = Instant.NONE,
    var updateAt: Instant? = Instant.NONE
) {

    fun isEmpty() = this == NONE

    companion object {
        val NONE = CwpVote()
    }
}
