package com.crowdproj.vote.common.models

import kotlin.jvm.JvmInline

@JvmInline
value class CwpVoteComment(val value: String) {
    fun asString() = value

    companion object {
        val NONE = CwpVoteComment("")
    }
}
