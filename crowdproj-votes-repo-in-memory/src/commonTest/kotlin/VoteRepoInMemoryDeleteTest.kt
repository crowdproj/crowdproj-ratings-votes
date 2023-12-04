package com.crowdproj.vote.repoinmemory

import com.crowdproj.vote.common.repo.IVoteRepository
import com.crowdproj.vote.repo.tests.RepoVoteDeleteTest

class VoteRepoInMemoryDeleteTest : RepoVoteDeleteTest() {
    override val repo: IVoteRepository = VoteRepoInMemory(
        initObjects = initObjects
    )
}
