package com.crowdproj.vote.api.v1.mappers

import com.crowdproj.vote.api.v1.models.*
import com.crowdproj.vote.common.CwpVoteContext
import com.crowdproj.vote.common.models.*
import com.crowdproj.vote.common.stubs.CwpVoteStubs

fun CwpVoteContext.fromApi(request: IRequestVote) = when (request) {
    is VoteCreateRequest -> fromApi(request)
    is VoteReadRequest -> fromApi(request)
    is VoteUpdateRequest -> fromApi(request)
    is VoteDeleteRequest -> fromApi(request)
}

fun CwpVoteContext.fromApi(request: VoteCreateRequest) {
    resolveOperation(request)
    fromApiVoteCreate(request.vote)
    fromApiDebug(request)
}

fun CwpVoteContext.fromApi(request: VoteReadRequest) {
    resolveOperation(request)
    fromApiVoteRead(request.vote)
    fromApiDebug(request)
}

fun CwpVoteContext.fromApi(request: VoteUpdateRequest) {
    resolveOperation(request)
    fromApiVoteUpdate(request.vote)
    fromApiDebug(request)
}

fun CwpVoteContext.fromApi(request: VoteDeleteRequest) {
    resolveOperation(request)
    fromApiVoteDelete(request.vote)
    fromApiDebug(request)
}

private fun CwpVoteContext.fromApiVoteCreate(vote: VoteCreateObject?) {
    this.voteRequest = vote?.let {
        CwpVote(
            userId = it.userId.fromApiUserId(),
            ratingId = it.ratingId.fromApiRatingId(),
            score = it.score.fromApiScore(),
            comment = it.comment.fromApiComment(),
            isAccepted = it.isAccept.fromApiIsAccepted()
        )
    } ?: CwpVote()
}

private fun Comment?.fromApiComment(): CwpVoteComment =
    this?.let {
        if (!this.id.isNullOrBlank() && !this.name.isNullOrBlank())
            CwpVoteComment(id = this.id!!, value = this.name!!)
        else CwpVoteComment.NONE
    } ?: CwpVoteComment.NONE

private fun Boolean?.fromApiIsAccepted(): CwpVoteIsAccepted =
    this?.let { CwpVoteIsAccepted(this) } ?: CwpVoteIsAccepted.NONE

private fun String?.fromApiScore(): CwpVoteScore = this?.let { CwpVoteScore(this) } ?: CwpVoteScore.NONE
private fun String?.fromApiRatingId(): CwpVoteRatingId = this?.let { CwpVoteRatingId(this) } ?: CwpVoteRatingId.NONE
private fun String?.fromApiUserId(): CwpVoteUserId = this?.let { CwpVoteUserId(this) } ?: CwpVoteUserId.NONE

private fun CwpVoteContext.fromApiVoteRead(vote: VoteReadObject?) {
    this.voteRequest.id = vote?.id?.toVoteId() ?: CwpVoteId.NONE
}

private fun CwpVoteContext.fromApiVoteUpdate(vote: VoteUpdateObject?) {
    this.voteRequest = vote?.let {
        CwpVote(
            id = it.id.toVoteId(),
            lock = it.lock.toVoteLock(),
            userId = it.userId.fromApiUserId(),
            ratingId = it.ratingId.fromApiRatingId(),
            comment = it.comment.fromApiComment(),
            score = it.score.fromApiScore(),
            isAccepted = it.isAccept.fromApiIsAccepted()
        )
    } ?: CwpVote()
}

private fun CwpVoteContext.fromApiVoteDelete(vote: VoteDeleteObject?) {
    this.voteRequest.id = vote?.id?.toVoteId() ?: CwpVoteId.NONE
    this.voteRequest.lock = vote?.lock?.toVoteLock() ?: CwpVoteLock.NONE
}

private fun String?.toVoteId() = this?.let { CwpVoteId(it) } ?: CwpVoteId.NONE
private fun String?.toVoteLock() = this?.let { CwpVoteLock(it) } ?: CwpVoteLock.NONE

private fun VoteRequestDebugMode?.fromApiWorkMode(): CwpVoteWorkMode = when (this) {
    VoteRequestDebugMode.PROD -> CwpVoteWorkMode.PROD
    VoteRequestDebugMode.TEST -> CwpVoteWorkMode.TEST
    VoteRequestDebugMode.STUB -> CwpVoteWorkMode.STUB
    null -> CwpVoteWorkMode.NONE
}

private fun VoteRequestDebugStubs?.fromApiStubCase(): CwpVoteStubs = when (this) {
    VoteRequestDebugStubs.SUCCESS -> CwpVoteStubs.SUCCESS
    VoteRequestDebugStubs.NOT_FOUND -> CwpVoteStubs.NOT_FOUND
    VoteRequestDebugStubs.BAD_ID -> CwpVoteStubs.BAD_ID
    VoteRequestDebugStubs.CANNOT_DELETE -> CwpVoteStubs.CANNOT_DELETE
    VoteRequestDebugStubs.BAD_SEARCH_STRING -> CwpVoteStubs.BAD_SEARCH_STRING
    null -> CwpVoteStubs.NONE
}

private fun CwpVoteContext.fromApiDebug(request: IRequestVote?) {
    this.workMode = request?.debug?.mode?.fromApiWorkMode() ?: CwpVoteWorkMode.NONE
    this.stubCase = request?.debug?.stub?.fromApiStubCase() ?: CwpVoteStubs.NONE
}

private fun CwpVoteContext.resolveOperation(request: IRequestVote) {
    this.command = when (request) {
        is VoteCreateRequest -> CwpVoteCommand.CREATE
        is VoteReadRequest -> CwpVoteCommand.READ
        is VoteUpdateRequest -> CwpVoteCommand.UPDATE
        is VoteDeleteRequest -> CwpVoteCommand.DELETE
    }
}
