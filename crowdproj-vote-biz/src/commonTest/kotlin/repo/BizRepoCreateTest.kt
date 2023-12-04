package com.crowdproj.vote.biz.repo

import com.crowdproj.vote.biz.CwpVoteProcessor
import com.crowdproj.vote.common.CwpVoteContext
import com.crowdproj.vote.common.config.CwpVoteCorSettings
import com.crowdproj.vote.common.models.*
import com.crowdproj.vote.common.repo.DbVoteResponse
import com.crowdproj.vote.repo.tests.VoteRepositoryMock
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class BizRepoCreateTest {

    private val userId = CwpVoteUserId("testUser")
    private val command = CwpVoteCommand.CREATE
    private val uuid = "10000000-0000-0000-0000-000000000001"
    private val repo = VoteRepositoryMock(
        invokeCreateVote = {
            DbVoteResponse(
                isSuccess = true,
                data = CwpVote(
                    id = CwpVoteId(uuid),
                    ratingId = CwpVoteRatingId("10"),
                    userId = userId,
                    score = CwpVoteScore("5"),
                    comment = CwpVoteComment(value = "test"),
                    isAccepted = CwpVoteIsAccepted(true)
                )
            )
        }
    )
    private val settings = CwpVoteCorSettings(
        repoTest = repo
    )
    private val processor = CwpVoteProcessor(settings)

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun repoCreateSuccessTest() = runTest {
        val ctx = CwpVoteContext(
            command = command,
            state = CwpVoteState.NONE,
            workMode = CwpVoteWorkMode.TEST,
            voteRequest = CwpVote(
                ratingId = CwpVoteRatingId("10"),
                userId = userId,
                score = CwpVoteScore("5"),
                comment = CwpVoteComment(value = "test"),
                isAccepted = CwpVoteIsAccepted(true)
            )
        )
        processor.exec(ctx)
        assertEquals(CwpVoteState.FINISHING, ctx.state)
        assertNotEquals(CwpVoteId.NONE, ctx.voteResponse.id)
        assertEquals("10", ctx.voteResponse.ratingId.asString())
        assertEquals("testUser", ctx.voteResponse.userId.asString())
        assertEquals("5", ctx.voteResponse.score.asString())
        assertEquals("test", ctx.voteResponse.comment.value)
        assertEquals("true", ctx.voteResponse.isAccepted.value().toString())
    }
}
