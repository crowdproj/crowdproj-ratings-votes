package com.crowdproj.vote.biz.validation

import com.crowdproj.vote.biz.CwpVoteProcessor
import com.crowdproj.vote.common.CwpVoteContext
import com.crowdproj.vote.common.models.*
import com.crowdproj.vote.common.models.CwpVoteCommand
import com.crowdproj.vote.stubs.CwpVoteStub
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

private val stub = CwpVoteStub.get()

@OptIn(ExperimentalCoroutinesApi::class)
fun validationScoreCorrect(command: CwpVoteCommand, processor: CwpVoteProcessor) = runTest {
    val ctx = CwpVoteContext(
        command = command,
        state = CwpVoteState.NONE,
        workMode = CwpVoteWorkMode.TEST,
        voteRequest = CwpVote(
            id = stub.id,
            ratingId = stub.ratingId,
            userId = stub.userId,
            score = CwpVoteScore("123")
        ),
    )
    if (command == CwpVoteCommand.UPDATE || command == CwpVoteCommand.DELETE) ctx.voteRequest.lock = stub.lock
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(CwpVoteState.FAILING, ctx.state)
    assertEquals("123", ctx.voteValidated.score.asString())
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationScoreEmpty(command: CwpVoteCommand, processor: CwpVoteProcessor) = runTest {
    val ctx = CwpVoteContext(
        command = command,
        state = CwpVoteState.NONE,
        workMode = CwpVoteWorkMode.TEST,
        voteRequest = CwpVote(
            id = stub.id,
            ratingId = stub.ratingId,
            userId = stub.userId,
            score = CwpVoteScore("")
        ),
    )
    if (command == CwpVoteCommand.UPDATE || command == CwpVoteCommand.DELETE) ctx.voteRequest.lock = stub.lock
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(CwpVoteState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("score", error?.field)
    assertContains(error?.message ?: "", "score")
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationScoreSymbols(command: CwpVoteCommand, processor: CwpVoteProcessor) = runTest {
    val ctx = CwpVoteContext(
        command = command,
        state = CwpVoteState.NONE,
        workMode = CwpVoteWorkMode.TEST,
        voteRequest = CwpVote(
            id = stub.id,
            ratingId = stub.ratingId,
            userId = stub.userId,
            score = CwpVoteScore("wrongFormat")
        ),
    )
    if (command == CwpVoteCommand.UPDATE || command == CwpVoteCommand.DELETE) ctx.voteRequest.lock = stub.lock
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(CwpVoteState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("score", error?.field)
    assertContains(error?.message ?: "", "score")
}
