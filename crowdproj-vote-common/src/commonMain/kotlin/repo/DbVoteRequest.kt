package com.crowdproj.vote.common.repo

import com.crowdproj.vote.common.models.CwpVote

data class DbVoteRequest(
    val vote: CwpVote
)
