package com.crowdproj.vote.stubs

import com.crowdproj.vote.common.models.CwpVote
import com.crowdproj.vote.stubs.CwpVoteStubProduct.VOTE_PRODUCT

object CwpVoteStub {
    fun get(): CwpVote = VOTE_PRODUCT.copy()

    fun prepareResult(block: CwpVote.() -> Unit): CwpVote = get().apply(block)
}
