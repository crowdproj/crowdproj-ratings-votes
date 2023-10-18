package com.crowdproj.vote.common

import com.crowdproj.vote.common.config.CwpVoteCorSettings
import com.crowdproj.vote.common.models.*
import com.crowdproj.vote.common.repo.IVoteRepository
import com.crowdproj.vote.common.stubs.CwpVoteStubs
import kotlinx.datetime.Instant

data class CwpVoteContext(
    var command: CwpVoteCommand = CwpVoteCommand.NONE,
    var state: CwpVoteState = CwpVoteState.NONE,
    val errors: MutableList<CwpVoteError> = mutableListOf(),
    var timeStart: Instant = Instant.DISTANT_PAST,
    var settings: CwpVoteCorSettings = CwpVoteCorSettings.NONE,
    var workMode: CwpVoteWorkMode = CwpVoteWorkMode.PROD,
    var stubCase: CwpVoteStubs = CwpVoteStubs.NONE,
    var requestId: CwpVoteRequestId = CwpVoteRequestId.NONE,
    var voteRepo: IVoteRepository = IVoteRepository.NONE,
    var voteComment: CwpVoteComment = CwpVoteComment.NONE,
    var voteRequest: CwpVote = CwpVote(),

    var voteValidating: CwpVote = CwpVote(),

    var voteValidated: CwpVote = CwpVote(),

    var voteRepoReVote: CwpVote = CwpVote(),
    var voteRepoPrepare: CwpVote = CwpVote(),
    var voteRepoDone: CwpVote = CwpVote(),
    var votesRepoDone: MutableList<CwpVote> = mutableListOf(),

    var voteResponse: CwpVote = CwpVote(),
    var votesResponse: MutableList<CwpVote> = mutableListOf(),
)
