package com.crowdproj.vote.api.v1.mappers

import com.crowdproj.vote.api.v1.models.IRequestVote
import com.crowdproj.vote.api.v1.models.VoteDebug
import com.crowdproj.vote.api.v1.models.VoteDeleteObject
import com.crowdproj.vote.api.v1.models.VoteDeleteRequest
import com.crowdproj.vote.api.v1.models.VoteDeleteResponse
import com.crowdproj.vote.api.v1.models.VoteRequestDebugMode
import com.crowdproj.vote.api.v1.models.VoteRequestDebugStubs
import com.crowdproj.vote.common.CwpVoteContext
import com.crowdproj.vote.common.models.CwpVote
import com.crowdproj.vote.common.models.CwpVoteCommand
import com.crowdproj.vote.common.models.CwpVoteComment
import com.crowdproj.vote.common.models.CwpVoteId
import com.crowdproj.vote.common.models.CwpVoteIsAccepted
import com.crowdproj.vote.common.models.CwpVoteLock
import com.crowdproj.vote.common.models.CwpVoteRatingId
import com.crowdproj.vote.common.models.CwpVoteRequestId
import com.crowdproj.vote.common.models.CwpVoteScore
import com.crowdproj.vote.common.models.CwpVoteUserId
import com.crowdproj.vote.common.models.CwpVoteWorkMode
import com.crowdproj.vote.common.stubs.CwpVoteStubs
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class CwpVoteDeleteTest {
    @Test
    fun request() {
        val api = VoteDeleteRequest(
            debug = VoteDebug(
                mode = VoteRequestDebugMode.STUB,
                stub = VoteRequestDebugStubs.BAD_ID,
            ),
            vote = VoteDeleteObject(
                id = "123",
                lock = "12356",
            ),
        )
        val ctx = CwpVoteContext()
        ctx.fromApi(api as IRequestVote)
        assertEquals(CwpVoteWorkMode.STUB, ctx.workMode)
        assertEquals(CwpVoteStubs.BAD_ID, ctx.stubCase)
        assertEquals("123", ctx.voteRequest.id.asString())
        assertEquals("12356", ctx.voteRequest.lock.asString())
        assertEquals(CwpVoteCommand.DELETE, ctx.command)
    }

    @Test
    fun response() {
        val now: Instant = Clock.System.now()
        val ctx = CwpVoteContext(
            requestId = CwpVoteRequestId("111"),
            command = CwpVoteCommand.DELETE,
            voteResponse = CwpVote(
                id = CwpVoteId("123"),
                lock = CwpVoteLock("234"),
                ratingId = CwpVoteRatingId("10"),
                userId = CwpVoteUserId("11"),
                score = CwpVoteScore("5"),
                comment = CwpVoteComment(value = "test"),
                isAccepted = CwpVoteIsAccepted(true),
                createAt = now,
                updateAt = now
            )
        )
        val api = ctx.toApi()
        assertIs<VoteDeleteResponse>(api)
        assertEquals("111", api.requestId)
        assertEquals("234", api.vote?.lock)
        assertEquals("123", api.vote?.id)
        assertEquals("10", api.vote?.ratingId)
        assertEquals("11", api.vote?.userId)
        assertEquals("5", api.vote?.score)
        assertEquals("test", api.vote?.comment)
        assertEquals(now.toString(), api.vote?.createdAt)
        assertEquals(now.toString(), api.vote?.updatedAt)
    }
}
