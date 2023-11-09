package com.crowdproj.vote.biz.validation

import com.crowdproj.vote.biz.CwpVoteProcessor
import com.crowdproj.vote.common.config.CwpVoteCorSettings
import com.crowdproj.vote.common.models.CwpVoteCommand
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlin.test.Test

// TODO-validation-5: смотрим пример теста валидации, собранного из тестовых функций-оберток
@OptIn(ExperimentalCoroutinesApi::class)
class BizValidationCreateTest {

    private val command = CwpVoteCommand.CREATE
    private val settings by lazy {
        CwpVoteCorSettings()
    }
    private val processor by lazy { CwpVoteProcessor(settings) }

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

    @Test
    fun correctCommentId() = validationCommentIdCorrect(command, processor)

    @Test
    fun badSymbolsCommentId() = validationCommentIdSymbols(command, processor)
}
