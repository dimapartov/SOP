package com.example.sop.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMQConfiguration {

    public static final String EXCHANGE_NAME = "ordersExchange";
    public static final String CREATE_QUEUE_NAME = "createQueue";
    public static final String UPDATE_QUEUE_NAME = "updateQueue";
    public static final String DELETE_QUEUE_NAME = "deleteQueue";


    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }


    @Bean
    public Queue createQueue() {
        return new Queue(CREATE_QUEUE_NAME, true);
    }

    @Bean
    public Queue updateQueue() {
        return new Queue(UPDATE_QUEUE_NAME, true);
    }

    @Bean
    public Queue deleteQueue() {
        return new Queue(DELETE_QUEUE_NAME, true);
    }


    @Bean
    public TopicExchange ordersExchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }


    @Bean
    public Binding createBinding(Queue createQueue, TopicExchange ordersExchange) {
        return BindingBuilder.bind(createQueue).to(ordersExchange).with("orders.create");
    }

    @Bean
    public Binding updateBinding(Queue updateQueue, TopicExchange ordersExchange) {
        return BindingBuilder.bind(updateQueue).to(ordersExchange).with("orders.update");
    }

    @Bean
    public Binding deleteBinding(Queue deleteQueue, TopicExchange ordersExchange) {
        return BindingBuilder.bind(deleteQueue).to(ordersExchange).with("orders.delete");
    }
}