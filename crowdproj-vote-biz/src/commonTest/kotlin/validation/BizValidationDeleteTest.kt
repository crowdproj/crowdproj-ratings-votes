package com.crowdproj.vote.biz.validation

import com.crowdproj.vote.biz.CwpVoteProcessor
import com.crowdproj.vote.common.config.CwpVoteCorSettings
import com.crowdproj.vote.common.models.CwpVoteCommand
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlin.test.Test

// TODO-validation-5: смотрим пример теста валидации, собранного из тестовых функций-оберток
@OptIn(ExperimentalCoroutinesApi::class)
class BizValidationDeleteTest {

    private val command = CwpVoteCommand.DELETE
    private val settings by lazy {
        CwpVoteCorSettings()
    }
    private val processor by lazy { CwpVoteProcessor(settings) }

    @Test
    fun correctId() = validationIdCorrect(command, processor)

    @Test
    fun emptyId() = validationIdEmpty(command, processor)

    @Test
    fun badSymbolsId() = validationIdFormat(command, processor)
}
