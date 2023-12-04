package com.crowdproj.vote.app.spring.controller

import com.crowdproj.vote.api.v1.mappers.toApiCreate
import com.crowdproj.vote.api.v1.mappers.toApiDelete
import com.crowdproj.vote.api.v1.mappers.toApiRead
import com.crowdproj.vote.api.v1.mappers.toApiUpdate
import com.crowdproj.vote.api.v1.models.IRequestVote
import com.crowdproj.vote.api.v1.models.IResponseVote
import com.crowdproj.vote.api.v1.models.VoteCreateRequest
import com.crowdproj.vote.api.v1.models.VoteDeleteRequest
import com.crowdproj.vote.api.v1.models.VoteReadRequest
import com.crowdproj.vote.api.v1.models.VoteUpdateRequest
import com.crowdproj.vote.app.spring.CwpVoteBlockingProcessor
import com.crowdproj.vote.app.spring.config.CorConfig
import com.crowdproj.vote.biz.CwpVoteProcessor
import com.crowdproj.vote.common.CwpVoteContext
import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.BodyInserters

@WebFluxTest(VotesController::class)
@Import(CorConfig::class)
internal class VoteControllerTest {

    @Autowired
    private lateinit var webClient: WebTestClient

    @MockkBean(relaxUnitFun = true)
    lateinit var processor: CwpVoteProcessor

    @MockkBean(relaxUnitFun = true)
    lateinit var cwpVoteBlockingProcessor: CwpVoteBlockingProcessor

    @Autowired
    private lateinit var mapper: ObjectMapper

    @Test
    fun createVote() = testStubVote(
        "/api/v1/vote/create",
        VoteCreateRequest(),
        CwpVoteContext().toApiCreate()
    )

    @Test
    fun readVote() = testStubVote(
        "/api/v1/vote/read",
        VoteReadRequest(),
        CwpVoteContext().toApiRead()
    )

    @Test
    fun updateVote() = testStubVote(
        "/api/v1/vote/update",
        VoteUpdateRequest(),
        CwpVoteContext().toApiUpdate()
    )

    @Test
    fun deleteVote() = testStubVote(
        "/api/v1/vote/delete",
        VoteDeleteRequest(),
        CwpVoteContext().toApiDelete()
    )

    private inline fun <reified Req : IRequestVote, reified Res : IResponseVote> testStubVote(
        url: String,
        requestObj: Req,
        responseObj: Res,
    ) {
        webClient
            .post()
            .uri(url)
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue(requestObj))
            .exchange()
            .expectStatus().isOk
            .expectBody(Res::class.java)
            .value {
                println("RESPONSE: $it")
                Assertions.assertThat(it).isEqualTo(responseObj)
            }
    }
}
