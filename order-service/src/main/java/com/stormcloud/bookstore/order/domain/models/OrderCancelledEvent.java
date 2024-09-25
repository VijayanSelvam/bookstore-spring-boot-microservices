package com.stormcloud.bookstore.order.domain.models;

import java.time.LocalDateTime;
import java.util.Set;

public record OrderCancelledEvent(
        String eventId,
        String orderNumber,
        Set<OrderItem> items,
        Customer customer,
        Address deliveryAddress,
        String reason,
        LocalDateTime createdAt) {}
