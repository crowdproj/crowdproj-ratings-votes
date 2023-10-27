package com.crowdproj.vote.common.models

import kotlin.jvm.JvmInline

@JvmInline
value class CwpVoteUserId(private val id: String) {
    fun asString() = id

    companion object {
        val NONE = CwpVoteUserId("")
    }
}
