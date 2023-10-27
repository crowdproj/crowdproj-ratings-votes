package com.crowdproj.vote.common.models

import kotlin.jvm.JvmInline

@JvmInline
value class CwpVoteIsAccepted(private val value: Boolean) {
    fun value() = value

    companion object {
        val NONE = CwpVoteIsAccepted(false)
    }
}
