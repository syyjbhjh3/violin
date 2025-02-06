package com.api.kubernetes.common.util.rabbitMQ;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RabbitMqConsumer {

    @Value("${rabbitmq.queue.name}")
    private String queueName;

    @RabbitListener(queues = "${rabbitmq.queue.name}")
    public void receiveMessage(String message) {
        log.info("Received message from {}: {}", queueName, message);
        processMessage(message);
    }

    private void processMessage(String message) {
        log.info("Processing message: {}", message);
    }
}
