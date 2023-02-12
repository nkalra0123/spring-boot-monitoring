package com.example.kafka.demo.kafka.consumer;

import com.example.kafka.demo.pojo.TestMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.retrytopic.DltStrategy;
import org.springframework.kafka.retrytopic.TopicSuffixingStrategy;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;

@Component
public class KafkaMessageConsumer {
    private static final Logger logger = LoggerFactory.getLogger(KafkaMessageConsumer.class);

    public KafkaMessageConsumer() {
    }

    @RetryableTopic(
        backoff = @Backoff(value = 3000L),
        autoCreateTopics = "false", attempts = "2",
        topicSuffixingStrategy = TopicSuffixingStrategy.SUFFIX_WITH_INDEX_VALUE,
        dltStrategy = DltStrategy.FAIL_ON_ERROR)
    @KafkaListener(topics = "${spring.kafka.topics.job-request}", groupId = "${spring.kafka.consumer.group-id}",  concurrency = "4")
    public void consume(TestMsg testMsg, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) throws Exception {
        logger.info("Received request in KafkaMessageConsumer.testMsg :: {} at topic :: {}", testMsg, topic);
        Thread.sleep(400);
    }

    @DltHandler
    public void dlt(TestMsg testMsg) {
        logger.error("Failed request while parsing, maximum retry reached {}", testMsg);
    }
}
