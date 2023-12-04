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

@OptIn(ExperimentalCoroutinesApi::class)
class BizRepoUpdateTest {

    private val userId = CwpVoteUserId("testUser")
    private val command = CwpVoteCommand.UPDATE
    private val initAd = CwpVote(
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
                    data = initAd,
                )
            },
            invokeUpdateVote = {
                DbVoteResponse(
                    isSuccess = true,
                    data = CwpVote(
                        id = CwpVoteId("123"),
                        ratingId = CwpVoteRatingId("10"),
                        userId = userId,
                        score = CwpVoteScore("6"),
                        comment = CwpVoteComment(value = "updated"),
                        isAccepted = CwpVoteIsAccepted(true)
                    )
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

    @Test
    fun repoUpdateSuccessTest() = runTest {
        val voteToUpdate = CwpVote(
            id = CwpVoteId("123"),
            ratingId = CwpVoteRatingId("10"),
            userId = userId,
            score = CwpVoteScore("6"),
            comment = CwpVoteComment(value = "updated"),
            isAccepted = CwpVoteIsAccepted(true)
        )
        val ctx = CwpVoteContext(
            command = command,
            state = CwpVoteState.NONE,
            workMode = CwpVoteWorkMode.TEST,
            voteRequest = voteToUpdate,
        )
        processor.exec(ctx)
        assertEquals(CwpVoteState.FINISHING, ctx.state)
        assertEquals(voteToUpdate.id, ctx.voteResponse.id)
        assertEquals(voteToUpdate.ratingId, ctx.voteResponse.ratingId)
        assertEquals(voteToUpdate.userId, ctx.voteResponse.userId)
        assertEquals(voteToUpdate.score, ctx.voteResponse.score)
        assertEquals(voteToUpdate.comment, ctx.voteResponse.comment)
        assertEquals(voteToUpdate.isAccepted, ctx.voteResponse.isAccepted)
    }

    @Test
    fun repoUpdateNotFoundTest() = repoNotFoundTest(command)
}
