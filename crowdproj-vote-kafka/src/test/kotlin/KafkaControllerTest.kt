package com.crowdproj.vote.kafka

import com.crowdproj.vote.api.v1.apiV1RequestSerialize
import com.crowdproj.vote.api.v1.apiV1ResponseDeserialize
import com.crowdproj.vote.api.v1.models.VoteCreateObject
import com.crowdproj.vote.api.v1.models.VoteCreateRequest
import com.crowdproj.vote.api.v1.models.VoteCreateResponse
import com.crowdproj.vote.api.v1.models.VoteDebug
import com.crowdproj.vote.api.v1.models.VoteRequestDebugMode
import com.crowdproj.vote.api.v1.models.VoteRequestDebugStubs
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.clients.consumer.MockConsumer
import org.apache.kafka.clients.consumer.OffsetResetStrategy
import org.apache.kafka.clients.producer.MockProducer
import org.apache.kafka.common.TopicPartition
import org.apache.kafka.common.serialization.StringSerializer
import org.junit.Test
import java.util.*
import kotlin.test.assertEquals

class KafkaControllerTest {
    @Test
    fun runKafka() {
        val consumer = MockConsumer<String, String>(OffsetResetStrategy.EARLIEST)
        val producer = MockProducer<String, String>(true, StringSerializer(), StringSerializer())
        val config = AppKafkaConfig()
        val inputTopic = config.kafkaTopicInV1
        val outputTopic = config.kafkaTopicOutV1

        val app = AppKafkaConsumer(config, listOf(ConsumerStrategyV1()), consumer = consumer, producer = producer)

        consumer.schedulePollTask {
            consumer.rebalance(Collections.singletonList(TopicPartition(inputTopic, 0)))
            consumer.addRecord(
                ConsumerRecord(
                    inputTopic,
                    PARTITION,
                    0L,
                    "test-1",
                    apiV1RequestSerialize(
                        VoteCreateRequest(
                            vote = VoteCreateObject(
                                ratingId = "1",
                                userId = "2",
                                score = "3",
                                comment = "testComment"
                            ),
                            debug = VoteDebug(
                                mode = VoteRequestDebugMode.STUB,
                                stub = VoteRequestDebugStubs.SUCCESS
                            )
                        )
                    )
                )
            )
            app.stop()
        }
        val startOffsets: MutableMap<TopicPartition, Long> = mutableMapOf()
        val tp = TopicPartition(inputTopic, PARTITION)
        startOffsets[tp] = 0L
        consumer.updateBeginningOffsets(startOffsets)

        app.run()

        val message = producer.history().first()
        val result = apiV1ResponseDeserialize<VoteCreateResponse>(message.value())

        assertEquals(outputTopic, message.topic())
        assertEquals("1", result.vote?.ratingId)
        assertEquals("2", result.vote?.userId)
        assertEquals("3", result.vote?.score)
        assertEquals("testComment", result.vote?.comment)
    }

    companion object {
        const val PARTITION = 0
    }
}
