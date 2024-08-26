// package com.stormcloud.bookstore.order.domain.web.controllers;
//
// import com.stormcloud.bookstore.order.ApplicationProperties;
// import org.springframework.amqp.rabbit.core.RabbitTemplate;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RestController;
//
// @RestController
// class RabbitMQController {
//    private final RabbitTemplate rabbitTemplate;
//    private final ApplicationProperties properties;
//
//    RabbitMQController(RabbitTemplate rabbitTemplate, ApplicationProperties properties) {
//        this.rabbitTemplate = rabbitTemplate;
//        this.properties = properties;
//    }
//
//    @PostMapping("/message/send")
//    public void sendMessage(@RequestBody MyMessage message) {
//        rabbitTemplate.convertAndSend(properties.orderEventExchange(), message.routingKey(), message.payload());
//    }
//
//    record MyMessage(String routingKey, MyPayload payload) {}
//
//    record MyPayload(String content) {}
// }
