package com.example.sop.services;

import com.example.sop.config.RabbitMQConfiguration;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class RabbitMQPublisherService {

    private final RabbitTemplate rabbitTemplate;


    @Autowired
    public RabbitMQPublisherService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }


    public void sendMessage(String routingKey, Object message) {
        try {
            rabbitTemplate.convertAndSend(RabbitMQConfiguration.EXCHANGE_NAME, routingKey, message);
            System.out.println("Message sent successfully: " + message);
        }
        catch (AmqpException e) {
            System.err.println("Failed to send message: " + e.getMessage());
        }
        catch (Exception e) {
            System.err.println("Unknown error:" + e.getMessage());
        }
    }
}