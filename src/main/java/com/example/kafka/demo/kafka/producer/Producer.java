package com.example.kafka.demo.kafka.producer;

import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;


@Service
public interface Producer<T> {
    void sendMessage(String topic, T messageObject) throws ExecutionException, InterruptedException;
}
