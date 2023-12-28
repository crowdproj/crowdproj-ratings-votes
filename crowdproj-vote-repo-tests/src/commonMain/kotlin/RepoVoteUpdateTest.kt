package com.crowdproj.vote.repo.tests

import com.crowdproj.vote.common.models.*
import com.crowdproj.vote.common.models.CwpVoteComment
import com.crowdproj.vote.common.models.CwpVoteLock
import com.crowdproj.vote.common.models.CwpVoteRatingId
import com.crowdproj.vote.common.models.CwpVoteScore
import com.crowdproj.vote.common.models.CwpVoteUserId
import com.crowdproj.vote.common.repo.DbVoteRequest
import com.crowdproj.vote.common.repo.IVoteRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
abstract class RepoVoteUpdateTest {
    abstract val repo: IVoteRepository
    protected open val updateSucc = initObjects[0]
    protected open val updateConc = initObjects[1]
    protected val updateIdNotFound = CwpVoteId("vote-repo-update-not-found")
    protected val lockBad = CwpVoteLock("20000000-0000-0000-0000-000000000009")
    protected val lockNew = CwpVoteLock("20000000-0000-0000-0000-000000000002")

    private val reqUpdateSucc by lazy {
        CwpVote(
            id = updateSucc.id,
            ratingId = CwpVoteRatingId("10"),
            userId = CwpVoteUserId("testUser"),
            score = CwpVoteScore("5"),
            comment = CwpVoteComment(value = "test"),
            lock = initObjects.first().lock,
        )
    }
    private val reqUpdateNotFound = CwpVote(
        id = updateIdNotFound,
        ratingId = CwpVoteRatingId("10"),
        userId = CwpVoteUserId("11"),
        score = CwpVoteScore("5"),
        comment = CwpVoteComment(value = "test"),
        lock = initObjects.first().lock,
    )
    private val reqUpdateConc by lazy {
        CwpVote(
            id = updateConc.id,
            ratingId = CwpVoteRatingId("10"),
            userId = CwpVoteUserId("11"),
            score = CwpVoteScore("5"),
            comment = CwpVoteComment(value = "test"),
            lock = lockBad,
        )
    }

    @Test
    fun updateSuccess() = runRepoTest {
        val result = repo.updateVote(DbVoteRequest(reqUpdateSucc))
        assertEquals(true, result.isSuccess)
        assertEquals(reqUpdateSucc.id, result.data?.id)
        assertEquals(reqUpdateSucc.userId, result.data?.userId)
        assertEquals(reqUpdateSucc.ratingId, result.data?.ratingId)
        assertEquals(reqUpdateSucc.score, result.data?.score)
        assertEquals(reqUpdateSucc.comment.value, result.data?.comment?.value)
        assertEquals(emptyList(), result.errors)
        assertEquals(lockNew, result.data?.lock)
    }

    @Test
    fun updateNotFound() = runRepoTest {
        val result = repo.updateVote(DbVoteRequest(reqUpdateNotFound))
        assertEquals(false, result.isSuccess)
        assertEquals(null, result.data)
        val error = result.errors.find { it.code == "not-found" }
        assertEquals("id", error?.field)
    }

    @Test
    fun updateConcurrencyError() = runRepoTest {
        val result = repo.updateVote(DbVoteRequest(reqUpdateConc))
        assertEquals(false, result.isSuccess)
        val error = result.errors.find { it.code == "concurrency" }
        assertEquals("lock", error?.field)
        assertEquals(updateConc, result.data)
    }

    companion object : BaseInitVotes("update") {
        override val initObjects: List<CwpVote> = listOf(
            createInitTestModel("update"),
            createInitTestModel("updateConc"),
        )
    }
}
