package com.crowdproj.vote.app.spring.controller

import com.crowdproj.vote.api.v1.mappers.toApiCreate
import com.crowdproj.vote.api.v1.mappers.toApiDelete
import com.crowdproj.vote.api.v1.mappers.toApiRead
import com.crowdproj.vote.api.v1.mappers.toApiUpdate
import com.crowdproj.vote.api.v1.models.VoteCreateRequest
import com.crowdproj.vote.api.v1.models.VoteDeleteRequest
import com.crowdproj.vote.api.v1.models.VoteReadRequest
import com.crowdproj.vote.api.v1.models.VoteUpdateRequest
import com.crowdproj.vote.common.CwpVoteContext
import com.crowdproj.vote.common.stubs.CwpVoteStub
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

// Temporary simple test with stubs
@WebMvcTest(VotesController::class)
internal class VoteControllerTest {

    @Autowired
    private lateinit var mvc: MockMvc

    @Autowired
    private lateinit var mapper: ObjectMapper

    @Test
    fun createVote() = testStubVote(
        "/api/v1/vote/create",
        VoteCreateRequest(),
        CwpVoteContext().apply { voteResponse = CwpVoteStub.fullStub }.toApiCreate()
    )

    @Test
    fun readVote() = testStubVote(
        "/api/v1/vote/read",
        VoteReadRequest(),
        CwpVoteContext().apply { voteResponse = CwpVoteStub.fullStub }.toApiRead()
    )

    @Test
    fun updateVote() = testStubVote(
        "/api/v1/vote/update",
        VoteUpdateRequest(),
        CwpVoteContext().apply { voteResponse = CwpVoteStub.fullStub }.toApiUpdate()
    )

    @Test
    fun deleteVote() = testStubVote(
        "/api/v1/vote/delete",
        VoteDeleteRequest(),
        CwpVoteContext().apply { voteResponse = CwpVoteStub.fullStub }.toApiDelete()
    )

    private fun <Req : Any, Res : Any> testStubVote(
        url: String,
        requestObj: Req,
        responseObj: Res,
    ) {
        val request = mapper.writeValueAsString(requestObj)
        val response = mapper.writeValueAsString(responseObj)

        println("request = $request")
        println("response = $response")

        mvc.perform(
            MockMvcRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(request)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().json(response))
    }
}
