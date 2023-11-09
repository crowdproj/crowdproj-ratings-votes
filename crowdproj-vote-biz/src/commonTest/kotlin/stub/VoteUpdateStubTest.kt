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
class VoteUpdateStubTest {

    private val processor = CwpVoteProcessor()
    val id = CwpVoteId("666")
    val ratingId = "1"
    val userId = "userid"
    val score = "2"

    @Test
    fun create() = runTest {

        val ctx = CwpVoteContext(
            command = CwpVoteCommand.CREATE,
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
            command = CwpVoteCommand.UPDATE,
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
            command = CwpVoteCommand.UPDATE,
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
    fun `badCommentId - wrong format`() = runTest {
        val ctx = CwpVoteContext(
            command = CwpVoteCommand.UPDATE,
            state = CwpVoteState.NONE,
            workMode = CwpVoteWorkMode.STUB,
            stubCase = CwpVoteStubs.BAD_COMMENT_ID,
            voteRequest = CwpVote(
                id = id,
                ratingId = CwpVoteRatingId(ratingId),
                userId = CwpVoteUserId(userId),
                score = CwpVoteScore(score),
                comment = CwpVoteComment("$%^&*", "comment")
            )
        )
        processor.exec(ctx)
        assertEquals(CwpVote(), ctx.voteResponse)
        assertEquals("commentId", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }


    @Test
    fun badRatingId() = runTest {
        val ctx = CwpVoteContext(
            command = CwpVoteCommand.CREATE,
            state = CwpVoteState.NONE,
            workMode = CwpVoteWorkMode.STUB,
            stubCase = CwpVoteStubs.BAD_RATING_ID,
            voteRequest = CwpVote(
                id = id,
                ratingId = CwpVoteRatingId(""),
                userId = CwpVoteUserId(userId),
                score = CwpVoteScore(score)
            )
        )
        processor.exec(ctx)
        assertEquals(CwpVote(), ctx.voteResponse)
        assertEquals("ratingId", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun `badRatingId - wrong format`() = runTest {
        val ctx = CwpVoteContext(
            command = CwpVoteCommand.CREATE,
            state = CwpVoteState.NONE,
            workMode = CwpVoteWorkMode.STUB,
            stubCase = CwpVoteStubs.BAD_RATING_ID,
            voteRequest = CwpVote(
                id = id,
                ratingId = CwpVoteRatingId("wrong format"),
                userId = CwpVoteUserId(userId),
                score = CwpVoteScore(score)
            )
        )
        processor.exec(ctx)
        assertEquals(CwpVote(), ctx.voteResponse)
        assertEquals("ratingId", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun badUserId() = runTest {
        val ctx = CwpVoteContext(
            command = CwpVoteCommand.CREATE,
            state = CwpVoteState.NONE,
            workMode = CwpVoteWorkMode.STUB,
            stubCase = CwpVoteStubs.BAD_USER_ID,
            voteRequest = CwpVote(
                id = id,
                ratingId = CwpVoteRatingId(ratingId),
                userId = CwpVoteUserId(""),
                score = CwpVoteScore(score)
            )
        )
        processor.exec(ctx)
        assertEquals(CwpVote(), ctx.voteResponse)
        assertEquals("userId", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun `badUserId - wrong format`() = runTest {
        val ctx = CwpVoteContext(
            command = CwpVoteCommand.CREATE,
            state = CwpVoteState.NONE,
            workMode = CwpVoteWorkMode.STUB,
            stubCase = CwpVoteStubs.BAD_USER_ID,
            voteRequest = CwpVote(
                id = id,
                ratingId = CwpVoteRatingId(ratingId),
                userId = CwpVoteUserId("1234"),
                score = CwpVoteScore(score)
            )
        )
        processor.exec(ctx)
        assertEquals(CwpVote(), ctx.voteResponse)
        assertEquals("userId", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun badScore() = runTest {
        val ctx = CwpVoteContext(
            command = CwpVoteCommand.CREATE,
            state = CwpVoteState.RUNNING,
            workMode = CwpVoteWorkMode.STUB,
            stubCase = CwpVoteStubs.BAD_SCORE,
            voteRequest = CwpVote(
                id = id,
                ratingId = CwpVoteRatingId(ratingId),
                userId = CwpVoteUserId(userId),
                score = CwpVoteScore("")
            )
        )
        processor.exec(ctx)
        assertEquals(CwpVote(), ctx.voteResponse)
        assertEquals("score", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun `badScore - wrong format`() = runTest {
        val ctx = CwpVoteContext(
            command = CwpVoteCommand.CREATE,
            state = CwpVoteState.RUNNING,
            workMode = CwpVoteWorkMode.STUB,
            stubCase = CwpVoteStubs.BAD_SCORE,
            voteRequest = CwpVote(
                id = id,
                ratingId = CwpVoteRatingId(ratingId),
                userId = CwpVoteUserId(userId),
                score = CwpVoteScore("wrong format")
            )
        )
        processor.exec(ctx)
        assertEquals(CwpVote(), ctx.voteResponse)
        assertEquals("score", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun badNoCase() = runTest {
        val ctx = CwpVoteContext(
            command = CwpVoteCommand.UPDATE,
            state = CwpVoteState.RUNNING,
            workMode = CwpVoteWorkMode.STUB,
            stubCase = CwpVoteStubs.BAD_SEARCH_STRING,
            voteRequest = CwpVote(
                id = id,
                ratingId = CwpVoteRatingId(ratingId),
                userId = CwpVoteUserId(userId),
                score = CwpVoteScore(score)
            )
        )
        processor.exec(ctx)
        assertEquals(CwpVote(), ctx.voteResponse)
        assertEquals("stub", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }
}
