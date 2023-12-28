package com.crowdproj.vote.repo.tests

import com.crowdproj.vote.common.models.CwpVote
import com.crowdproj.vote.common.models.CwpVoteId
import com.crowdproj.vote.common.repo.DbVoteIdRequest
import com.crowdproj.vote.common.repo.IVoteRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
abstract class RepoVoteReadTest {
    abstract val repo: IVoteRepository
    protected open val readSucc = initObjects[0]

    @Test
    fun readSuccess() = runRepoTest {
        val result = repo.readVote(DbVoteIdRequest(readSucc.id))

        assertEquals(true, result.isSuccess)
        assertEquals(readSucc, result.data)
        assertEquals(emptyList(), result.errors)
    }

    @Test
    fun readNotFound() = runRepoTest {
        val result = repo.readVote(DbVoteIdRequest(notFoundId))

        assertEquals(false, result.isSuccess)
        assertEquals(null, result.data)
        val error = result.errors.find { it.code == "not-found" }
        assertEquals("id", error?.field)
    }

    companion object : BaseInitVotes("delete") {
        override val initObjects: List<CwpVote> = listOf(
            createInitTestModel("read")
        )

        val notFoundId = CwpVoteId("vote-repo-read-notFound")
    }
}
