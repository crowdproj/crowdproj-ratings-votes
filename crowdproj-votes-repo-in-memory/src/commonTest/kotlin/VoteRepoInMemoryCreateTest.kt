package com.crowdproj.vote.repoinmemory

import com.crowdproj.vote.repo.tests.RepoVoteCreateTest

class VoteRepoInMemoryCreateTest : RepoVoteCreateTest() {
    override val repo = VoteRepoInMemory(
        initObjects = initObjects,
        randomUuid = { lockNew.asString() }
    )
}
