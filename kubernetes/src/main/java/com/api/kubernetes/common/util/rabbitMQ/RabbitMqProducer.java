package com.api.kubernetes.common.util.rabbitMQ;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class RabbitMqProducer {

    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange.name}")
    private String exchangeName;

    public void sendMessage(String routingKey, Object message) {
        rabbitTemplate.convertAndSend(exchangeName, routingKey, message);
        log.debug("Mesage sent successfully : {}", message);
    }
}
