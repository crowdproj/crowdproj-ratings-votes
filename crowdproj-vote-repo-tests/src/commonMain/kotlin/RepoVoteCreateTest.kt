package com.crowdproj.vote.repo.tests

import com.crowdproj.vote.common.models.*
import com.crowdproj.vote.common.repo.DbVoteRequest
import com.crowdproj.vote.common.repo.IVoteRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

@OptIn(ExperimentalCoroutinesApi::class)
abstract class RepoVoteCreateTest {
    abstract val repo: IVoteRepository

    protected open val lockNew: CwpVoteLock = CwpVoteLock("20000000-0000-0000-0000-000000000002")

    private val createObj = CwpVote(
        ratingId = CwpVoteRatingId("10"),
        userId = CwpVoteUserId("testUser"),
        score = CwpVoteScore("5"),
        comment = CwpVoteComment(value = "test")
    )

    @Test
    fun createSuccess() = runRepoTest {
        val result = repo.createVote(DbVoteRequest(createObj))
        val expected = createObj.copy(id = result.data?.id ?: CwpVoteId.NONE)
        assertEquals(true, result.isSuccess)
        assertEquals(expected.ratingId, result.data?.ratingId)
        assertEquals(expected.userId, result.data?.userId)
        assertEquals(expected.score, result.data?.score)
        assertEquals(expected.comment.value, result.data?.comment?.value)
        assertNotEquals(CwpVoteId.NONE, result.data?.id)
        assertEquals(emptyList(), result.errors)
        assertEquals(lockNew, result.data?.lock)
    }

    companion object : BaseInitVotes("create") {
        override val initObjects: List<CwpVote> = emptyList()
    }
}
