package com.crowdproj.vote.common.models

enum class CwpVoteCommand(val isUpdatable: Boolean) {
    NONE(isUpdatable = false),
    CREATE(isUpdatable = true),
    READ(isUpdatable = false),
    UPDATE(isUpdatable = true),
    DELETE(isUpdatable = true)
    ;
}
