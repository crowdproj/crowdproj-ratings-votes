package com.crowdproj.vote.common.repo

import com.crowdproj.vote.common.models.CwpVoteError

interface IDbResponse<T> {
    val data: T?
    val isSuccess: Boolean
    val errors: List<CwpVoteError>
}
