package com.example.kafka.demo.kafka.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import java.util.concurrent.ExecutionException;

@Service
public class KafkaProducer<T> implements Producer<T> {

    private static final Logger logger = LoggerFactory.getLogger(KafkaProducer.class);
    private final KafkaTemplate<String, T> kafkaTemplate;

    public KafkaProducer(KafkaTemplate<String, T> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String topic, T messageObject) throws ExecutionException, InterruptedException {
        Message<T> message = MessageBuilder
            .withPayload(messageObject)
            .setHeader(KafkaHeaders.TOPIC, topic)
            .build();
        try {
            kafkaTemplate.send(message).get();
            logger.info("Message sent to topic: {} message: {}", topic, message);
        } catch (InterruptedException | ExecutionException e) {
            logger.error("Exception while sending Kafka message topic: {} message: {}", topic, message);
            throw e;
        }
    }
}
