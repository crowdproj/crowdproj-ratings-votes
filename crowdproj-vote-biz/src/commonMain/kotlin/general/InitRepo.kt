package com.crowdproj.vote.biz.general

import com.crowdproj.vote.common.CwpVoteContext
import com.crowdproj.vote.common.helpers.errorAdministration
import com.crowdproj.vote.common.helpers.fail
import com.crowdproj.vote.common.models.CwpVoteWorkMode
import com.crowdproj.vote.common.repo.IVoteRepository
import com.crowdproj.kotlin.cor.handlers.worker
import com.crowdproj.kotlin.cor.ICorAddExecDsl

fun ICorAddExecDsl<CwpVoteContext>.initRepo(title: String) = worker() {
    this.title = title
    description = "Определение репозитория на основе режима работы"
    handle {
        voteRepo = when {
            workMode == CwpVoteWorkMode.TEST -> settings.repoTest
            workMode == CwpVoteWorkMode.STUB -> settings.repoStub
            else -> settings.repoProd
        }

        if (workMode != CwpVoteWorkMode.STUB && voteRepo == IVoteRepository.Companion.NONE) fail(
            errorAdministration(
                field = "repo",
                violationCode = "dbNotConfigured",
                description = "Репозиторий не настроен для $workMode"
            )
        )
    }
}
