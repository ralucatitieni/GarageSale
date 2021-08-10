package com.portfolio.model.response;

import com.portfolio.ProductTemplate;
import com.portfolio.entity.order.Order;
import com.portfolio.entity.product.Product;
import com.portfolio.model.response.order.OrderResponse;
import com.portfolio.model.response.order.OrderResponseFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class OrderResponseFactoryTest {

    private final ResponseFactoryProvider responseFactoryProvider = new ResponseFactoryProvider();
    @Mock
    private Order order;
    // TODO: 5/10/2021  create mock
    private Product product;
    private OrderResponseFactory orderResponseFactory;

    @BeforeEach
    void setup() {
        orderResponseFactory = new OrderResponseFactory(responseFactoryProvider);
        // TODO: 5/10/2021 create Product mock
        product = ProductTemplate.createHeadphone();
        Set<Product> products = new HashSet<>(Arrays.asList(product));
        when(order.getId()).thenReturn(1);
        when(order.getProducts()).thenReturn(products);
        when(order.getTotal()).thenReturn(135.0);

    }

    @Test
    void createResponse() {
        OrderResponse response = orderResponseFactory.createResponse(order);
        assertNotNull(response);
        assertEquals(1, response.getId());
        assertEquals(1, response.getProductResponses().size());
        assertEquals(135, response.getTotalAmount());
    }
}
