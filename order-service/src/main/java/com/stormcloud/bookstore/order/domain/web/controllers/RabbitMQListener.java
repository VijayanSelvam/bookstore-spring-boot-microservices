//package com.stormcloud.bookstore.order.domain.web.controllers;
//
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.stereotype.Service;
//
//@Service
//public class RabbitMQListener {
//
//    @RabbitListener(queues = "${order.new-order.queue}")
//    public void handleNewOrder(RabbitMQController.MyPayload payload) {
//        System.out.println("New Order: " + payload.content());
//    }
//
//    @RabbitListener(queues = "${order.delivered-order.queue}")
//    public void handleDeliveredOrder(RabbitMQController.MyPayload payload) {
//        System.out.println("Delivered Order: " + payload.content());
//    }
//}
