package com.crowdproj.vote.stubs

import com.crowdproj.vote.common.models.CwpVote
import com.crowdproj.vote.common.models.CwpVoteId
import com.crowdproj.vote.common.models.CwpVoteRatingId
import com.crowdproj.vote.stubs.CwpVoteStubProduct.VOTE_PRODUCT

object CwpVoteStub {
    fun get(): CwpVote = VOTE_PRODUCT.copy()

    fun prepareResult(block: CwpVote.() -> Unit): CwpVote = get().apply(block)

    fun prepareSearchList(filter: String) = listOf(
        cwpVote("pr-111-01", filter),
        cwpVote("pr-111-02", filter),
        cwpVote("pr-111-03", filter),
        cwpVote("pr-111-04", filter),
        cwpVote("pr-111-05", filter),
        cwpVote("pr-111-06", filter),
    )

    private fun cwpVote(id: String, filter: String) = voteCopy(VOTE_PRODUCT, id = id, ratingId = filter)

    private fun voteCopy(base: CwpVote, id: String, ratingId: String) = base.copy(
        id = CwpVoteId(id),
        ratingId = CwpVoteRatingId(ratingId),
    )
}
