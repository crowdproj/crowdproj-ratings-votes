package com.crowdproj.vote.biz.stub

import com.crowdproj.vote.biz.CwpVoteProcessor
import com.crowdproj.vote.common.CwpVoteContext
import com.crowdproj.vote.common.models.*
import com.crowdproj.vote.common.stubs.CwpVoteStubs
import com.crowdproj.vote.stubs.CwpVoteStub
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class VoteReadStubTest {

    private val processor = CwpVoteProcessor()
    val id = CwpVoteId("666")
    val ratingId = "1"
    val userId = "userid"
    val score = "2"

    @Test
    fun create() = runTest {
        val ctx = CwpVoteContext(
            command = CwpVoteCommand.READ,
            state = CwpVoteState.NONE,
            workMode = CwpVoteWorkMode.STUB,
            stubCase = CwpVoteStubs.SUCCESS,
            voteRequest = CwpVote(
                id = id,
                ratingId = CwpVoteRatingId(ratingId),
                userId = CwpVoteUserId(userId),
                score = CwpVoteScore(score)
            )
        )
        processor.exec(ctx)
        assertEquals(CwpVoteStub.get().id, ctx.voteResponse.id)
        assertEquals(ratingId, ctx.voteResponse.ratingId.asString())
        assertEquals(userId, ctx.voteResponse.userId.asString())
        assertEquals(score, ctx.voteResponse.score.asString())
    }

    @Test
    fun badId() = runTest {
        val ctx = CwpVoteContext(
            command = CwpVoteCommand.READ,
            state = CwpVoteState.NONE,
            workMode = CwpVoteWorkMode.STUB,
            stubCase = CwpVoteStubs.BAD_ID,
            voteRequest = CwpVote()
        )
        processor.exec(ctx)
        assertEquals(CwpVote(), ctx.voteResponse)
        assertEquals("id", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun `badId - wrong format`() = runTest {
        val ctx = CwpVoteContext(
            command = CwpVoteCommand.READ,
            state = CwpVoteState.NONE,
            workMode = CwpVoteWorkMode.STUB,
            stubCase = CwpVoteStubs.BAD_ID,
            voteRequest = CwpVote(
                id = CwpVoteId("*$%&("),
                ratingId = CwpVoteRatingId(ratingId),
                userId = CwpVoteUserId(userId),
                score = CwpVoteScore(score)
            )
        )
        processor.exec(ctx)
        assertEquals(CwpVote(), ctx.voteResponse)
        assertEquals("id", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun badNoCase() = runTest {
        val ctx = CwpVoteContext(
            command = CwpVoteCommand.READ,
            state = CwpVoteState.RUNNING,
            workMode = CwpVoteWorkMode.STUB,
            stubCase = CwpVoteStubs.BAD_RATING_ID,
            voteRequest = CwpVote(
                id = id
            )
        )
        processor.exec(ctx)
        assertEquals(CwpVote(), ctx.voteResponse)
        assertEquals("stub", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }
}
