package com.crowdproj.vote.common.config

import com.crowdproj.vote.common.repo.IVoteRepository

data class CwpVoteCorSettings(
    val repoTest: IVoteRepository = IVoteRepository.NONE,
    val repoStub: IVoteRepository = IVoteRepository.NONE,
    val repoProd: IVoteRepository = IVoteRepository.NONE,
) {
    companion object {
        val NONE = CwpVoteCorSettings()
    }
}
