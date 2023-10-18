package com.crowdproj.vote.api.v1.mappers

import com.crowdproj.vote.api.v1.models.*
import com.crowdproj.vote.common.CwpVoteContext
import com.crowdproj.vote.common.models.*

fun CwpVoteContext.toApi(): IResponseVote? = when (command) {
    CwpVoteCommand.NONE -> null
    CwpVoteCommand.CREATE -> toApiCreate()
    CwpVoteCommand.READ -> toApiRead()
    CwpVoteCommand.UPDATE -> toApiUpdate()
    CwpVoteCommand.DELETE -> toApiDelete()
}

fun CwpVoteContext.toApiCreate() = VoteCreateResponse(
    requestId = toApiRequestId(),
    result = toApiResult(),
    errors = toApiErrors(),
    vote = toApiVote(this.voteResponse),
)

fun CwpVoteContext.toApiRead() = VoteReadResponse(
    requestId = toApiRequestId(),
    result = toApiResult(),
    errors = toApiErrors(),
    vote = toApiVote(this.voteResponse),
)

fun CwpVoteContext.toApiUpdate() = VoteUpdateResponse(
    requestId = toApiRequestId(),
    result = toApiResult(),
    errors = toApiErrors(),
    vote = toApiVote(this.voteResponse),
)

fun CwpVoteContext.toApiDelete() = VoteDeleteResponse(
    requestId = toApiRequestId(),
    result = toApiResult(),
    errors = toApiErrors(),
    vote = toApiVote(this.voteResponse),
)

private fun toApiVote(vote: CwpVote): VoteResponseObject? = if (vote.isEmpty()) {
    null
} else {
    VoteResponseObject(
        id = vote.id.takeIf { it != CwpVoteId.NONE }?.asString(),
        userId = vote.userId.takeIf { it != CwpVoteUserId.NONE }?.asString(),
        ratingId = vote.ratingId.takeIf { it != CwpVoteRatingId.NONE }?.asString(),
        score = vote.score.takeIf { it != CwpVoteScore.NONE }?.asString(),
        comment = vote.comment.takeIf { it != CwpVoteComment.NONE }?.toApiComment(),
        isAccept = vote.isAccepted.takeIf { it != CwpVoteIsAccepted.NONE }?.value(),
        createdAt = vote.createAt.toString(),
        updatedAt = vote.updateAt.toString(),
        lock = vote.lock.takeIf { it != CwpVoteLock.NONE }?.asString(),
    )
}

private fun CwpVoteComment?.toApiComment(): Comment? =
    this?.let { Comment(this.id, this.value) }

private fun CwpVoteContext.toApiErrors(): List<Error>? = errors.map { it.toApiError() }.takeIf { it.isNotEmpty() }
private fun CwpVoteError.toApiError() = Error(
    code = this.code.trString(),
    group = this.group.trString(),
    field = this.field.trString(),
    title = this.message.trString(),
    description = null,
)

private fun String.trString(): String? = takeIf { it.isNotBlank() }

private fun CwpVoteContext.toApiResult(): ResponseResult? = when (this.state) {
    CwpVoteState.NONE -> null
    CwpVoteState.RUNNING -> ResponseResult.SUCCESS
    CwpVoteState.FAILING -> ResponseResult.ERROR
    CwpVoteState.FINISHING -> ResponseResult.SUCCESS
}

private fun CwpVoteContext.toApiRequestId(): String? = this.requestId.takeIf { it != CwpVoteRequestId.NONE }?.asString()
