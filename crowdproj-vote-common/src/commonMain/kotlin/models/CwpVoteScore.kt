package com.crowdproj.vote.common.models

import kotlin.jvm.JvmInline

@JvmInline
value class CwpVoteScore(private val value: String) {
    fun asString() = value

    companion object {
        val NONE = CwpVoteScore("")
    }
}
