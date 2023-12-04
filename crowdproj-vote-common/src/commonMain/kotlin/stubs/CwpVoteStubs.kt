package com.crowdproj.vote.common.stubs

import com.crowdproj.vote.common.models.CwpVote
import com.crowdproj.vote.common.models.CwpVoteComment
import com.crowdproj.vote.common.models.CwpVoteId
import com.crowdproj.vote.common.models.CwpVoteIsAccepted
import com.crowdproj.vote.common.models.CwpVoteLock
import com.crowdproj.vote.common.models.CwpVoteRatingId
import com.crowdproj.vote.common.models.CwpVoteScore
import com.crowdproj.vote.common.models.CwpVoteUserId

enum class CwpVoteStubs {
    NONE,
    SUCCESS,
    NOT_FOUND,
    BAD_ID,
    BAD_USER_ID,
    BAD_RATING_ID,
    BAD_SCORE,
    BAD_COMMENT_ID,
    CANNOT_DELETE,
    BAD_SEARCH_STRING,
    DB_ERROR,
}

class CwpVoteStub {
    companion object {
        var fullStub = CwpVote(
            id = CwpVoteId("1"),
            lock = CwpVoteLock("testLock"),
            userId = CwpVoteUserId("2"),
            ratingId = CwpVoteRatingId("3"),
            score = CwpVoteScore("4"),
            comment = CwpVoteComment("testComment"),
            isAccepted = CwpVoteIsAccepted(true),
            createAt = null,
            updateAt = null
        )
    }
}
