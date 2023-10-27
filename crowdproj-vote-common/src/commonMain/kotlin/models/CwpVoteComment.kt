package com.crowdproj.vote.common.models

class CwpVoteComment(val id: String, val value: String) {
    fun asString() = "id = $id, comment = $value"

    companion object {
        val NONE = CwpVoteComment("", "")
    }
}
