package com.crowdproj.vote.api.v1.mappers

import com.crowdproj.vote.api.v1.models.IRequestVote
import com.crowdproj.vote.api.v1.models.VoteDebug
import com.crowdproj.vote.api.v1.models.VoteReadObject
import com.crowdproj.vote.api.v1.models.VoteReadRequest
import com.crowdproj.vote.api.v1.models.VoteReadResponse
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

class CwpVoteReadTest {
    @Test
    fun request() {
        val api = VoteReadRequest(
            debug = VoteDebug(
                mode = VoteRequestDebugMode.STUB,
                stub = VoteRequestDebugStubs.BAD_ID,
            ),
            vote = VoteReadObject(
                id = "123"
            ),
        )
        val ctx = CwpVoteContext()
        ctx.fromApi(api as IRequestVote)
        assertEquals(CwpVoteWorkMode.STUB, ctx.workMode)
        assertEquals(CwpVoteStubs.BAD_ID, ctx.stubCase)
        assertEquals("123", ctx.voteRequest.id.asString())
        assertEquals(CwpVoteCommand.READ, ctx.command)
    }

    @Test
    fun response() {
        val now: Instant = Clock.System.now()
        val ctx = CwpVoteContext(
            requestId = CwpVoteRequestId("111"),
            command = CwpVoteCommand.READ,
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
        assertIs<VoteReadResponse>(api)
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
