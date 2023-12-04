package com.crowdproj.vote.common.config

import com.crowdproj.vote.common.repo.IVoteRepository
import com.crowdproj.vote.logging.common.CwpLoggerProvider

data class CwpVoteCorSettings(
    val loggerProvider: CwpLoggerProvider = CwpLoggerProvider(),
    val repoTest: IVoteRepository = IVoteRepository.NONE,
    val repoStub: IVoteRepository = IVoteRepository.NONE,
    val repoProd: IVoteRepository = IVoteRepository.NONE,
) {
    companion object {
        val NONE = CwpVoteCorSettings()
    }
}
