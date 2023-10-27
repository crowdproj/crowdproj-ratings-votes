package com.crowdproj.vote.api.v1

import com.crowdproj.vote.api.v1.models.*
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class RequestSerializationTest {
    private val request = VoteCreateRequest(
        debug = VoteDebug(
            mode = VoteRequestDebugMode.STUB,
            stub = VoteRequestDebugStubs.SUCCESS,
        ),
        vote = VoteCreateObject(
            score = "1", ratingId = "11", userId = "testUser", comment = Comment(id = "111", name = "testComment")
        )
    )

    @Test
    fun serialize() {
        val json = Json.encodeToString(IRequest.serializer(), request)

        println(json)

        assertContains(json, Regex("\"mode\":\\s*\"stub\""))
        assertContains(json, Regex("\"stub\":\\s*\"success\""))
        assertContains(json, Regex("\"score\":\\s*\"1\""))
        assertContains(json, Regex("\"ratingId\":\\s*\"11\""))
        assertContains(json, Regex("\"userId\":\\s*\"testUser\""))
        assertContains(json, Regex("\"id\":\\s*\"111\""))
        assertContains(json, Regex("\"name\":\\s*\"testComment\""))
    }

    @Test
    fun deserialize() {
        val json = Json.encodeToString(IRequest.serializer(), request)
        val obj = Json.decodeFromString(IRequest.serializer(), json) as VoteCreateRequest

        assertEquals(request, obj)
    }
}
