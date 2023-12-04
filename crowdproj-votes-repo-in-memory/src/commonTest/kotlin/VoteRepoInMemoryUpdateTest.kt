package com.crowdproj.vote.repoinmemory

import com.crowdproj.vote.common.repo.IVoteRepository
import com.crowdproj.vote.repo.tests.RepoVoteUpdateTest

class VoteRepoInMemoryUpdateTest : RepoVoteUpdateTest() {
    override val repo: IVoteRepository = VoteRepoInMemory(
        initObjects = initObjects,
        randomUuid = { lockNew.asString() }
    )
}
