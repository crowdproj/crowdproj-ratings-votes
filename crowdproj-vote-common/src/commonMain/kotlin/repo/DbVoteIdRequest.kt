package com.crowdproj.ad.common.repo

import com.crowdproj.vote.common.models.CwpVote
import com.crowdproj.vote.common.models.CwpVoteId
import com.crowdproj.vote.common.models.CwpVoteLock

data class DbVoteIdRequest(
    val id: CwpVoteId,
    val lock: CwpVoteLock = CwpVoteLock.NONE,
) {
    constructor(vote: CwpVote) : this(vote.id, vote.lock)
}
