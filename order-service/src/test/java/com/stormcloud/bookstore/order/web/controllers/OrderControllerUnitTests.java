package com.stormcloud.bookstore.order.web.controllers;

import static com.stormcloud.bookstore.order.testdata.TestDataFactory.createOrderRequestWithInvalidCustomer;
import static com.stormcloud.bookstore.order.testdata.TestDataFactory.createOrderRequestWithInvalidDeliveryAddress;
import static com.stormcloud.bookstore.order.testdata.TestDataFactory.createOrderRequestWithNoItems;
import static org.junit.jupiter.api.Named.named;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stormcloud.bookstore.order.domain.OrderService;
import com.stormcloud.bookstore.order.domain.SecurityService;
import com.stormcloud.bookstore.order.domain.models.CreateOrderRequest;
import com.stormcloud.bookstore.order.domain.web.controllers.OrderController;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(OrderController.class)
class OrderControllerUnitTests {
    @MockBean
    private OrderService orderService;

    @MockBean
    private SecurityService securityService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        given(securityService.getLoginUserName()).willReturn("vijayan");
    }

    @ParameterizedTest(name = "[{index}]-{0}")
    @MethodSource("createOrderRequestProvider")
    void shouldReturnBadRequestWhenOrderPayloadIsInvalid(CreateOrderRequest request) throws Exception {
        given(orderService.createOrder(eq("vijayan"), any(CreateOrderRequest.class)))
                .willReturn(null);

        mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    static Stream<Arguments> createOrderRequestProvider() {
        return Stream.of(
                arguments(named("Order with Invalid Customer", createOrderRequestWithInvalidCustomer())),
                arguments(named("Order with Invalid Delivery Address", createOrderRequestWithInvalidDeliveryAddress())),
                arguments(named("Order with No Items", createOrderRequestWithNoItems())));
    }
}
