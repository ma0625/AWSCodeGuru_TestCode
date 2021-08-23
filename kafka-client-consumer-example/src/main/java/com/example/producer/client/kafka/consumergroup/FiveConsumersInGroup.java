package com.example.producer.client.kafka.consumergroup;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class FiveConsumersInGroup {
    public static void main(String[] args) {
        int numConsumers = 5;
        ExecutorService executorService = Executors.newFixedThreadPool(numConsumers);

        IntStream.rangeClosed(1, numConsumers)
                .forEach(i -> {
                    ConsumeTask consumeTask = new ConsumeTask("myConsumer" + i, "five");
                    executorService.submit(consumeTask);
                });
    }
}

