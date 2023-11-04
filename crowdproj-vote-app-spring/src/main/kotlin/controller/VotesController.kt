package com.crowdproj.vote.app.spring.controller

import com.crowdproj.vote.api.v1.mappers.fromApi
import com.crowdproj.vote.api.v1.mappers.toApiCreate
import com.crowdproj.vote.api.v1.mappers.toApiDelete
import com.crowdproj.vote.api.v1.mappers.toApiRead
import com.crowdproj.vote.api.v1.mappers.toApiUpdate
import com.crowdproj.vote.api.v1.models.IResponseVote
import com.crowdproj.vote.api.v1.models.VoteCreateRequest
import com.crowdproj.vote.api.v1.models.VoteDeleteRequest
import com.crowdproj.vote.api.v1.models.VoteDeleteResponse
import com.crowdproj.vote.api.v1.models.VoteReadRequest
import com.crowdproj.vote.api.v1.models.VoteReadResponse
import com.crowdproj.vote.api.v1.models.VoteUpdateRequest
import com.crowdproj.vote.api.v1.models.VoteUpdateResponse
import com.crowdproj.vote.common.CwpVoteContext
import com.crowdproj.vote.common.stubs.CwpVoteStub
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/vote")
class VotesController {

    @PostMapping("/create")
    fun createVote(@RequestBody request: VoteCreateRequest): IResponseVote {
        val context = CwpVoteContext()
        context.fromApi(request)
        context.voteResponse = CwpVoteStub.fullStub
        return context.toApiCreate()
    }

    @PostMapping("/read")
    fun readVote(@RequestBody request: VoteReadRequest): VoteReadResponse {
        val context = CwpVoteContext()
        context.fromApi(request)
        context.voteResponse = CwpVoteStub.fullStub
        return context.toApiRead()
    }

    @RequestMapping("/update", method = [RequestMethod.POST])
    fun updateVote(@RequestBody request: VoteUpdateRequest): VoteUpdateResponse {
        val context = CwpVoteContext()
        context.fromApi(request)
        context.voteResponse = CwpVoteStub.fullStub
        return context.toApiUpdate()
    }

    @PostMapping("/delete")
    fun deleteVote(@RequestBody request: VoteDeleteRequest): VoteDeleteResponse {
        val context = CwpVoteContext()
        context.fromApi(request)
        context.voteResponse = CwpVoteStub.fullStub
        return context.toApiDelete()
    }
}
