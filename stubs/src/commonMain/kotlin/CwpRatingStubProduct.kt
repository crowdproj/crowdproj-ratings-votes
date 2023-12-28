package com.crowdproj.vote.stubs

import com.crowdproj.vote.common.models.*
import kotlinx.datetime.Instant

object CwpVoteStubProduct {
    val VOTE_PRODUCT: CwpVote
        get() = CwpVote(
            id = CwpVoteId("1"),
            lock = CwpVoteLock("testLock"),
            userId = CwpVoteUserId("userId"),
            ratingId = CwpVoteRatingId("3"),
            score = CwpVoteScore("4"),
            comment = CwpVoteComment("testComment"),
            isAccepted = CwpVoteIsAccepted(true),
            createAt = Instant.parse("2010-06-01T22:19:44.475Z"),
            updateAt = Instant.parse("2010-07-05T12:11:40.475Z")
        )
}
