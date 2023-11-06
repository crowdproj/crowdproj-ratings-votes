package com.crowdproj.vote.kafka

import com.crowdproj.vote.api.v1.apiV1RequestDeserialize
import com.crowdproj.vote.api.v1.apiV1ResponseSerialize
import com.crowdproj.vote.api.v1.mappers.fromApi
import com.crowdproj.vote.api.v1.mappers.toApi
import com.crowdproj.vote.api.v1.models.IRequestVote
import com.crowdproj.vote.api.v1.models.IResponseVote
import com.crowdproj.vote.common.CwpVoteContext

class ConsumerStrategyV1 : ConsumerStrategy {
    override fun topics(config: AppKafkaConfig): InputOutputTopics {
        return InputOutputTopics(config.kafkaTopicInV1, config.kafkaTopicOutV1)
    }

    override fun serialize(source: CwpVoteContext): String {
        val response: IResponseVote = source.toApi()!!
        return apiV1ResponseSerialize(response)
    }

    override fun deserialize(value: String, target: CwpVoteContext) {
        val request: IRequestVote = apiV1RequestDeserialize(value)
        target.fromApi(request)
    }
}
