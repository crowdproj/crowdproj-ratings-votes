package com.crowdproj.vote.app.spring.controller

import com.crowdproj.vote.api.v1.models.IResponseVote
import com.crowdproj.vote.api.v1.models.VoteCreateRequest
import com.crowdproj.vote.api.v1.models.VoteCreateResponse
import com.crowdproj.vote.api.v1.models.VoteDeleteRequest
import com.crowdproj.vote.api.v1.models.VoteDeleteResponse
import com.crowdproj.vote.api.v1.models.VoteReadRequest
import com.crowdproj.vote.api.v1.models.VoteReadResponse
import com.crowdproj.vote.api.v1.models.VoteUpdateRequest
import com.crowdproj.vote.api.v1.models.VoteUpdateResponse
import com.crowdproj.vote.app.spring.CwpVoteBlockingProcessor
import com.crowdproj.vote.common.models.CwpVoteCommand
import com.crowdproj.vote.logging.common.CwpLoggerProvider
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/vote")
class VotesController(
    private val loggerProvider: CwpLoggerProvider,
    private val processor: CwpVoteBlockingProcessor
) {

    @PostMapping("/create")
    suspend fun createVote(@RequestBody request: VoteCreateRequest): VoteCreateResponse =
        processV1(
            processor,
            CwpVoteCommand.CREATE,
            request = request,
            loggerProvider.logger(VotesController::class),
            "vote-create"
        )

    @PostMapping("/read")
    suspend fun readVote(@RequestBody request: VoteReadRequest): VoteReadResponse =
        processV1(
            processor,
            CwpVoteCommand.READ,
            request = request,
            loggerProvider.logger(VotesController::class),
            "vote-read"
        )

    @RequestMapping("/update", method = [RequestMethod.POST])
    suspend fun updateVote(@RequestBody request: VoteUpdateRequest): VoteUpdateResponse =
        processV1(
            processor,
            CwpVoteCommand.UPDATE,
            request = request,
            loggerProvider.logger(VotesController::class),
            "vote-update"
        )

    @PostMapping("/delete")
    suspend fun deleteVote(@RequestBody request: VoteDeleteRequest): VoteDeleteResponse =
        processV1(
            processor,
            CwpVoteCommand.DELETE,
            request = request,
            loggerProvider.logger(VotesController::class),
            "vote-delete"
        )
}
