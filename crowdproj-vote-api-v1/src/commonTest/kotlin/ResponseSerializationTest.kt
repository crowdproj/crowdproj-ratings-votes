package com.crowdproj.vote.api.v1

import com.crowdproj.vote.api.v1.models.*
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class ResponseSerializationTest {

    private val response = VoteCreateResponse(
        requestId = "123",
        result = ResponseResult.SUCCESS,
        errors = emptyList(),
        vote = VoteResponseObject(
            id = "1",
            ratingId = "11",
            userId = "testUser",
            score = "111",
            comment = "testComment",
            isAccept = true,
            createdAt = "10.10.2023",
            updatedAt = "11.10.2023"
        )
    )

    @Test
    fun serialize() {
        val json = Json.encodeToString(IResponseVote.serializer(), response)

        println(json)

        assertContains(json, Regex("\"responseType\":\\s*\"create\""))
        assertContains(json, Regex("\"requestId\":\\s*\"123\""))
        assertContains(json, Regex("\"result\":\\s*\"success\""))
        assertContains(json, Regex("\"id\":\\s*\"1\""))
        assertContains(json, Regex("\"ratingId\":\\s*\"11\""))
        assertContains(json, Regex("\"userId\":\\s*\"testUser\""))
        assertContains(json, Regex("\"comment\":\\s*\"testComment\""))
        assertContains(json, Regex("\"score\":\\s*\"111\""))
        assertContains(json, Regex("\"isAccept\":true"))
        assertContains(json, Regex("\"createdAt\":\\s*\"10.10.2023\""))
        assertContains(json, Regex("\"updatedAt\":\\s*\"11.10.2023\""))
    }

    @Test
    fun deserialize() {
        val json = Json.encodeToString(IResponseVote.serializer(), response)
        val obj = Json.decodeFromString(IResponseVote.serializer(), json) as VoteCreateResponse

        assertEquals(response, obj)
    }
}
