package com.example.kafka.demo.controller;

import com.example.kafka.demo.pojo.TestMsg;
import com.example.kafka.demo.KafkaTopics;
import com.example.kafka.demo.kafka.producer.Producer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class Controller {

    private static final Logger logger = LoggerFactory.getLogger(Controller.class);
    private final Producer<TestMsg> producer;
    private final KafkaTopics kafkaTopics;

    AtomicInteger counter = new AtomicInteger(0);

    public Controller(Producer<TestMsg> producer, KafkaTopics kafkaTopics) {
        this.producer = producer;
        this.kafkaTopics = kafkaTopics;
    }

    @GetMapping("/api/v1/notification")
    public String notification() {
        try {
            producer.sendMessage(kafkaTopics.getJobRequest(), new TestMsg(counter.incrementAndGet()));
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return "ok";
    }

}
