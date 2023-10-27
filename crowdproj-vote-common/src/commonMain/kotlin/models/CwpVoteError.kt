package com.crowdproj.vote.common.models

data class CwpVoteError(
    val code: String = "",
    val group: String = "",
    val field: String = "",
    val message: String = "",
    val level: Level = Level.ERROR,
    val exception: Throwable? = null,
) {
    enum class Level { INFO, WARN, ERROR }
}
