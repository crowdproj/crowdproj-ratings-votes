package com.crowdproj.vote.common.helpers

import com.crowdproj.vote.common.CwpVoteContext
import com.crowdproj.vote.common.exceptions.RepoConcurrencyException
import com.crowdproj.vote.common.models.CwpVoteError
import com.crowdproj.vote.common.models.CwpVoteLock
import com.crowdproj.vote.common.models.CwpVoteState

fun Throwable.asCwpVoteError(
    code: String = "unknown",
    group: String = "kotlin/exceptionseptions",
    message: String = this.message ?: "",
) = CwpVoteError(
    code = code,
    group = group,
    field = "",
    message = message,
    exception = this,
)

fun CwpVoteContext.addError(vararg error: CwpVoteError) = errors.addAll(error)

fun CwpVoteContext.fail(error: CwpVoteError) {
    addError(error)
    state = CwpVoteState.FAILING
}

fun errorValidation(
    field: String,
    /**
     * Код, характеризующий ошибку. Не должен включать имя поля или указание на валидацию.
     * Например: empty, badSymbols, tooLong, etc
     */
    violationCode: String,
    description: String,
    level: CwpVoteError.Level = CwpVoteError.Level.ERROR,
) = CwpVoteError(
    code = "validation-$field-$violationCode",
    field = field,
    group = "validation",
    message = "Validation error for field $field: $description",
    level = level,
)

fun errorAdministration(
    /**
     * Код, характеризующий ошибку. Не должен включать имя поля или указание на валидацию.
     * Например: empty, badSymbols, tooLong, etc
     */
    field: String = "",
    violationCode: String,
    description: String,
    level: CwpVoteError.Level = CwpVoteError.Level.ERROR,
) = CwpVoteError(
    field = field,
    code = "administration-$violationCode",
    group = "administration",
    message = "Microservice management error: $description",
    level = level,
)

fun errorRepoConcurrency(
    expectedLock: CwpVoteLock,
    actualLock: CwpVoteLock?,
    exception: Exception? = null,
) = CwpVoteError(
    field = "lock",
    code = "concurrency",
    group = "repo",
    message = "The object has been changed concurrently by another user or process",
    exception = exception ?: RepoConcurrencyException(expectedLock, actualLock),
)
