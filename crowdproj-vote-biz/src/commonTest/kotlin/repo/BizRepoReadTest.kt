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

class BizRepoReadTest {

    private val userId = CwpVoteUserId("testUser")
    private val command = CwpVoteCommand.READ
    private val initVote = CwpVote(
        id = CwpVoteId("123"),
        ratingId = CwpVoteRatingId("10"),
        userId = userId,
        score = CwpVoteScore("5"),
        comment = CwpVoteComment(value = "test"),
        isAccepted = CwpVoteIsAccepted(true)
    )
    private val repo by lazy {
        VoteRepositoryMock(
            invokeReadVote = {
                DbVoteResponse(
                    isSuccess = true,
                    data = initVote,
                )
            }
        )
    }
    private val settings by lazy {
        CwpVoteCorSettings(
            repoTest = repo
        )
    }
    private val processor by lazy { CwpVoteProcessor(settings) }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun repoReadSuccessTest() = runTest {
        val ctx = CwpVoteContext(
            command = command,
            state = CwpVoteState.NONE,
            workMode = CwpVoteWorkMode.TEST,
            voteRequest = CwpVote(
                id = CwpVoteId("123"),
            ),
        )
        processor.exec(ctx)
        assertEquals(CwpVoteState.FINISHING, ctx.state)
        assertEquals(initVote.id, ctx.voteResponse.id)
        assertEquals("10", ctx.voteResponse.ratingId.asString())
        assertEquals("testUser", ctx.voteResponse.userId.asString())
        assertEquals("5", ctx.voteResponse.score.asString())
        assertEquals("test", ctx.voteResponse.comment.value)
        assertEquals("true", ctx.voteResponse.isAccepted.value().toString())
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun repoReadNotFoundTest() = repoNotFoundTest(command)
}
