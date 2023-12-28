package com.crowdproj.vote.repoinmemory

import com.crowdproj.vote.common.repo.IVoteRepository
import com.crowdproj.vote.repo.tests.RepoVoteReadTest

class VoteRepoInMemoryReadTest : RepoVoteReadTest() {
    override val repo: IVoteRepository = VoteRepoInMemory(
        initObjects = initObjects
    )
}
