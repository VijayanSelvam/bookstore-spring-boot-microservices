package com.stormcloud.bookstore.order.domain;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(String message) {
        super(message);
    }

    public static OrderNotFoundException forCode(String orderId) {
        return new OrderNotFoundException("Order with order id " + orderId + " not found");
    }
}
