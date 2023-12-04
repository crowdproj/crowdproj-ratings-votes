package com.crowdproj.vote.repo.tests

import com.crowdproj.vote.common.models.*

abstract class BaseInitVotes(val op: String) : IInitObjects<CwpVote> {

    open val lockOld: CwpVoteLock = CwpVoteLock("20000000-0000-0000-0000-000000000001")
    open val lockBad: CwpVoteLock = CwpVoteLock("20000000-0000-0000-0000-000000000009")

    fun createInitTestModel(
        suf: String,
        userId: CwpVoteUserId = CwpVoteUserId("user-123"),
        ratingId: CwpVoteRatingId = CwpVoteRatingId("rating-123"),
        score: CwpVoteScore = CwpVoteScore("score-123"),
        lock: CwpVoteLock = lockOld,
    ) = CwpVote(
        id = CwpVoteId("vote-repo-$op-$suf"),
        ratingId = ratingId,
        userId = userId,
        score = score,
        isAccepted = CwpVoteIsAccepted(true),
        comment = CwpVoteComment("comment"),
        lock = lock,
    )
}
