package com.crowdproj.vote.common.models

import kotlin.jvm.JvmInline

@JvmInline
value class CwpVoteId(private val id: String) {
    fun asString() = id

    companion object {
        val NONE = CwpVoteId("")
    }
}
