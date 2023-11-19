package com.crowdproj.vote.api.v1

import com.crowdproj.vote.api.v1.models.IRequestVote
import com.crowdproj.vote.api.v1.models.IResponseVote
import kotlinx.serialization.json.Json

val cwpAdApiV1Json = Json {
    ignoreUnknownKeys = true
    useArrayPolymorphism = true
    coerceInputValues = true
}

fun apiV1RequestSerialize(request: IRequestVote): String = Json.encodeToString(IRequestVote.serializer(), request)

@Suppress("UNCHECKED_CAST")
fun <T : IRequestVote> apiV1RequestDeserialize(json: String): T =
    Json.decodeFromString(IRequestVote.serializer(), json) as T

fun apiV1ResponseSerialize(response: IResponseVote): String = Json.encodeToString(IResponseVote.serializer(), response)

@Suppress("UNCHECKED_CAST")
fun <T : IResponseVote> apiV1ResponseDeserialize(json: String): T =
    Json.decodeFromString(IResponseVote.serializer(), json) as T
