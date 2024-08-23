package com.stormcloud.bookstore.order;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "order")
public record ApplicationProperties(
        String catalogServiceUrl,
        String orderEventExchange,
        String newOrderQueue,
        String deliveredOrderQueue,
        String cancelledOrderQueue,
        String errorOrderQueue) {}
