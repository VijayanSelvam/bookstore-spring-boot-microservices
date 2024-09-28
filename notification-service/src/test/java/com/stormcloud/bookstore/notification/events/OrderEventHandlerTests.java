package com.stormcloud.bookstore.notification.events;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stormcloud.bookstore.notification.AbstractIT;
import com.stormcloud.bookstore.notification.ApplicationProperties;
import com.stormcloud.bookstore.notification.domain.models.*;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

class OrderEventHandlerTests extends AbstractIT {
    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    ApplicationProperties properties;

    Customer customer = new Customer("Vijayan", "vijay@gmail.com", "999999999");
    Address address = new Address("addr line 1", null, "Bangalore", "KA", "560040", "India");

    @Test
    void shouldHandleOrderCreatedEvent() {
        String orderNumber = UUID.randomUUID().toString();

        var event = new OrderCreatedEvent(
                UUID.randomUUID().toString(), orderNumber, Set.of(), customer, address, LocalDateTime.now());
        rabbitTemplate.convertAndSend(properties.orderEventExchange(), properties.newOrderQueue(), event);

        await().atMost(30, SECONDS).untilAsserted(() -> {
            verify(notificationService).sendOrderCreatedNotification(any(OrderCreatedEvent.class));
        });
    }

    @Test
    void shouldHandleOrderDeliveredEvent() {
        String orderNumber = UUID.randomUUID().toString();

        var event = new OrderDeliveredEvent(
                UUID.randomUUID().toString(), orderNumber, Set.of(), customer, address, LocalDateTime.now());
        rabbitTemplate.convertAndSend(properties.orderEventExchange(), properties.deliveredOrderQueue(), event);

        await().atMost(30, SECONDS).untilAsserted(() -> {
            verify(notificationService).sendOrderDeliveredNotification(any(OrderDeliveredEvent.class));
        });
    }

    @Test
    void shouldHandleOrderCancelledEvent() {
        String orderNumber = UUID.randomUUID().toString();

        var event = new OrderCancelledEvent(
                UUID.randomUUID().toString(),
                orderNumber,
                Set.of(),
                customer,
                address,
                "test cancel reason",
                LocalDateTime.now());
        rabbitTemplate.convertAndSend(properties.orderEventExchange(), properties.cancelledOrderQueue(), event);

        await().atMost(30, SECONDS).untilAsserted(() -> {
            verify(notificationService).sendOrderCancelledNotification(any(OrderCancelledEvent.class));
        });
    }

    @Test
    void shouldHandleOrderErrorEvent() {
        String orderNumber = UUID.randomUUID().toString();

        var event = new OrderErrorEvent(
                UUID.randomUUID().toString(),
                orderNumber,
                Set.of(),
                customer,
                address,
                "test error reason",
                LocalDateTime.now());
        rabbitTemplate.convertAndSend(properties.orderEventExchange(), properties.errorOrderQueue(), event);

        await().atMost(30, SECONDS).untilAsserted(() -> {
            verify(notificationService).sendOrderErrorEventNotification(any(OrderErrorEvent.class));
        });
    }
}
