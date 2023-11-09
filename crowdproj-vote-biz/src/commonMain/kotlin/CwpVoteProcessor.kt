package com.crowdproj.vote.biz

import com.crowdproj.vote.biz.general.operation
import com.crowdproj.vote.biz.general.prepareResult
import com.crowdproj.vote.biz.general.stubs
import com.crowdproj.vote.biz.validation.finishAdValidation
import com.crowdproj.vote.biz.validation.validateCommentIdProperFormat
import com.crowdproj.vote.biz.validation.validateIdNotEmpty
import com.crowdproj.vote.biz.validation.validateIdProperFormat
import com.crowdproj.vote.biz.validation.validateRatingIdHasContent
import com.crowdproj.vote.biz.validation.validateRatingIdNotEmpty
import com.crowdproj.vote.biz.validation.validateScoreHasContent
import com.crowdproj.vote.biz.validation.validateScoreNotEmpty
import com.crowdproj.vote.biz.validation.validateUserIdHasContent
import com.crowdproj.vote.biz.validation.validateUserIdNotEmpty
import com.crowdproj.vote.biz.validation.validation
import com.crowdproj.vote.biz.workers.initStatus
import com.crowdproj.vote.biz.workers.stubCreateSuccess
import com.crowdproj.vote.biz.workers.stubDeleteSuccess
import com.crowdproj.vote.biz.workers.stubReadSuccess
import com.crowdproj.vote.biz.workers.stubValidationBadCommentId
import com.crowdproj.vote.biz.workers.stubValidationBadId
import com.crowdproj.vote.biz.workers.stubValidationBadRatingId
import com.crowdproj.vote.biz.workers.stubValidationBadScore
import com.crowdproj.vote.biz.workers.stubValidationBadUserId
import com.crowdproj.vote.common.CwpVoteContext
import com.crowdproj.vote.common.config.CwpVoteCorSettings
import com.crowdproj.vote.common.models.CwpVoteCommand
import com.crowdproj.vote.common.models.CwpVoteId
import com.crowdproj.vote.lib.cor.rootChain
import com.crowdproj.vote.lib.cor.worker
import workers.stubNoCase

class CwpVoteProcessor(
    @Suppress("unused")
    private val corSettings: CwpVoteCorSettings = CwpVoteCorSettings.NONE
) {

    suspend fun exec(ctx: CwpVoteContext) =
        BusinessChain.exec(ctx.apply { settings = corSettings })

    companion object {
        private val BusinessChain = rootChain<CwpVoteContext> {
            initStatus("Инициализация статуса")

            operation("Создание объявления", CwpVoteCommand.CREATE) {
                stubs("Обработка стабов") {
                    stubCreateSuccess("Имитация успешной обработки")
                    stubValidationBadRatingId("Имитация ошибки валидации идентификатора рейтинга")
                    stubValidationBadUserId("Имитация ошибки валидации идентификатора пользователя")
                    stubValidationBadScore("Имитация ошибки валидации значения голоса")
                    stubValidationBadCommentId("Имитация ошибки валидации значения идентификатора комментария")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
                validation {
                    worker("Копируем поля в voteValidating") { voteValidating = voteRequest.copy() }
                    worker("Очистка id") { voteValidating.id = CwpVoteId.NONE }
                    validateRatingIdNotEmpty("Проверка, что идентификатор рейтинга не пуст")
                    validateRatingIdHasContent("Проверка символов в идентификаторе рейтинга")
                    validateUserIdNotEmpty("Проверка, что идентификатор пользователя не пустой")
                    validateUserIdHasContent("Проверка символов в идентификаторе пользователя")
                    validateScoreNotEmpty("Проверка, что голос не пустой")
                    validateScoreHasContent("Проверка символов голоса")
                    validateCommentIdProperFormat("Проверка идентификатора комментария")
                    finishAdValidation("Завершение проверок")
                }

                prepareResult("Подготовка ответа")
            }
            operation("Получить объявление", CwpVoteCommand.READ) {
                stubs("Обработка стабов") {
                    stubReadSuccess("Имитация успешной обработки")
                    stubValidationBadId("Имитация ошибки валидации id")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
                validation {
                    worker("Копируем поля в adValidating") { voteValidating = voteRequest.copy() }
                    validateIdNotEmpty("Проверка на непустой id")
                    validateIdProperFormat("Проверка формата id")

                    finishAdValidation("Успешное завершение процедуры валидации")
                }
                prepareResult("Подготовка ответа")
            }
            operation("Изменить объявление", CwpVoteCommand.UPDATE) {
                stubs("Обработка стабов") {
                    stubReadSuccess("Имитация успешной обработки")
                    stubValidationBadId("Имитация ошибки валидации id")
                    stubValidationBadRatingId("Имитация ошибки валидации идентификатора рейтинга")
                    stubValidationBadUserId("Имитация ошибки валидации идентификатора пользователя")
                    stubValidationBadScore("Имитация ошибки валидации значения голоса")
                    stubValidationBadCommentId("Имитация ошибки валидации значения идентификатора комментария")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
                validation {
                    worker("Копируем поля в adValidating") { voteValidating = voteRequest.copy() }
                    validateIdNotEmpty("Проверка на непустой id")
                    validateIdProperFormat("Проверка формата id")
                    validateRatingIdNotEmpty("Проверка, что идентификатор рейтинга не пуст")
                    validateRatingIdHasContent("Проверка символов в идентификаторе рейтинга")
                    validateUserIdNotEmpty("Проверка, что идентификатор пользователя не пустой")
                    validateUserIdHasContent("Проверка символов в идентификаторе пользователя")
                    validateScoreNotEmpty("Проверка, что голос не пустой")
                    validateCommentIdProperFormat("Проверка идентификатора комментария")
                    validateScoreHasContent("Проверка символов голоса")

                    finishAdValidation("Успешное завершение процедуры валидации")
                }
                prepareResult("Подготовка ответа")
            }
            operation("Удалить объявление", CwpVoteCommand.DELETE) {
                stubs("Обработка стабов") {
                    stubDeleteSuccess("Имитация успешной обработки")
                    stubValidationBadId("Имитация ошибки валидации id")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
                validation {
                    worker("Копируем поля в adValidating") {
                        voteValidating = voteRequest.copy()
                    }
                    validateIdNotEmpty("Проверка на непустой id")
                    validateIdProperFormat("Проверка формата id")
                    finishAdValidation("Успешное завершение процедуры валидации")
                }
                prepareResult("Подготовка ответа")
            }
        }.build()
    }
}
