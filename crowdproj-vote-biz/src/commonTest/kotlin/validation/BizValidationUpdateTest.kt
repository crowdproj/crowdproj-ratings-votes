package com.crowdproj.vote.biz.validation

import com.crowdproj.vote.biz.CwpVoteProcessor
import com.crowdproj.vote.common.config.CwpVoteCorSettings
import com.crowdproj.vote.common.models.CwpVoteCommand
import com.crowdproj.vote.repo.stubs.VoteRepoStub
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class BizValidationUpdateTest {

    private val command = CwpVoteCommand.UPDATE
    private val settings by lazy {
        CwpVoteCorSettings(
            repoTest = VoteRepoStub()
        )
    }
    private val processor by lazy { CwpVoteProcessor(settings) }

    @Test
    fun correctId() = validationIdCorrect(command, processor)

    @Test
    fun emptyId() = validationIdEmpty(command, processor)

    @Test
    fun badSymbolsId() = validationIdFormat(command, processor)

    @Test
    fun correctRatingId() = validationRatingIdCorrect(command, processor)

    @Test
    fun emptyRatingId() = validationRatingIdEmpty(command, processor)

    @Test
    fun badSymbolsRatingId() = validationRatingIdSymbols(command, processor)

    @Test
    fun correctUserId() = validationUserIdCorrect(command, processor)

    @Test
    fun emptyUserId() = validationUserIdEmpty(command, processor)

    @Test
    fun badSymbolsUserId() = validationUserIdSymbols(command, processor)

    @Test
    fun correctScore() = validationScoreCorrect(command, processor)

    @Test
    fun emptyScore() = validationScoreEmpty(command, processor)

    @Test
    fun badSymbolsScore() = validationScoreSymbols(command, processor)
}
