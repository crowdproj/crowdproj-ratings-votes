package com.crowdproj.vote.biz.repo

import com.crowdproj.vote.biz.CwpVoteProcessor
import com.crowdproj.vote.common.CwpVoteContext
import com.crowdproj.vote.common.config.CwpVoteCorSettings
import com.crowdproj.vote.common.models.*
import com.crowdproj.vote.common.repo.DbVoteResponse
import com.crowdproj.vote.repo.tests.VoteRepositoryMock
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.assertEquals

private val initAd = CwpVote(
    id = CwpVoteId("123"),
    ratingId = CwpVoteRatingId("10"),
    userId = CwpVoteUserId("testUser"),
    score = CwpVoteScore("5"),
    comment = CwpVoteComment(value = "test"),
    isAccepted = CwpVoteIsAccepted(true)
)
private val repo = VoteRepositoryMock(
    invokeReadVote = {
        if (it.id == initAd.id) {
            DbVoteResponse(
                isSuccess = true,
                data = initAd,
            )
        } else DbVoteResponse(
            isSuccess = false,
            data = null,
            errors = listOf(CwpVoteError(message = "Not found", field = "id"))
        )
    }
)
private val settings by lazy {
    CwpVoteCorSettings(
        repoTest = repo
    )
}
private val processor by lazy { CwpVoteProcessor(settings) }

@OptIn(ExperimentalCoroutinesApi::class)
fun repoNotFoundTest(command: CwpVoteCommand) = runTest {
    val ctx = CwpVoteContext(
        command = command,
        state = CwpVoteState.NONE,
        workMode = CwpVoteWorkMode.TEST,
        voteRequest = CwpVote(
            id = CwpVoteId("456"),
            ratingId = CwpVoteRatingId("10"),
            userId = CwpVoteUserId("testUser"),
            score = CwpVoteScore("5"),
            comment = CwpVoteComment(value = "test"),
            isAccepted = CwpVoteIsAccepted(true)
        )
    )
    if (command == CwpVoteCommand.UPDATE || command == CwpVoteCommand.DELETE) ctx.voteRequest.lock =
        CwpVoteLock("testLock")
    processor.exec(ctx)
    assertEquals(CwpVoteState.FAILING, ctx.state)
    assertEquals(CwpVote(), ctx.voteResponse)
    assertEquals(1, ctx.errors.size)
    assertEquals("id", ctx.errors.first().field)
}
