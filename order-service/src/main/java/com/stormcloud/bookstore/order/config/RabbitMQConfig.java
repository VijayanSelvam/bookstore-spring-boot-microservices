package com.stormcloud.bookstore.order.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stormcloud.bookstore.order.ApplicationProperties;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
class RabbitMQConfig {
    private final ApplicationProperties properties;

    RabbitMQConfig(ApplicationProperties properties) {
        this.properties = properties;
    }

    @Bean
    @Lazy(false)
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    DirectExchange exchange() {
        System.out.println(properties.orderEventExchange());
        return new DirectExchange(properties.orderEventExchange());
    }

    @Bean
    Queue newOrderQueue() {
        return QueueBuilder.durable(properties.newOrderQueue()).build();
    }

    @Bean
    Binding newOrderQueueBinding() {
        return BindingBuilder.bind(newOrderQueue()).to(exchange()).with(properties.newOrderQueue());
    }

    @Bean
    Queue deliveredOrderQueue() {
        return QueueBuilder.durable(properties.deliveredOrderQueue()).build();
    }

    @Bean
    Binding deliveredOrderQueueBinding() {
        return BindingBuilder.bind(deliveredOrderQueue()).to(exchange()).with(properties.deliveredOrderQueue());
    }

    @Bean
    Queue cancelledOrderQueue() {
        return QueueBuilder.durable(properties.cancelledOrderQueue()).build();
    }

    @Bean
    Binding cancelledOrderQueueBinding() {
        return BindingBuilder.bind(cancelledOrderQueue()).to(exchange()).with(properties.cancelledOrderQueue());
    }

    @Bean
    Queue errorOrderQueue() {
        return QueueBuilder.durable(properties.errorOrderQueue()).build();
    }

    @Bean
    Binding errorOrderQueueBinding() {
        return BindingBuilder.bind(errorOrderQueue()).to(exchange()).with(properties.errorOrderQueue());
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, ObjectMapper objectMapper) {
        final var rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jacksonConverter(objectMapper));
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter jacksonConverter(ObjectMapper mapper) {
        return new Jackson2JsonMessageConverter(mapper);
    }
}
