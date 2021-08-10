package com.portfolio.service;

import com.portfolio.OrderTemplate;
import com.portfolio.entity.order.Order;
import com.portfolio.exception.InvalidRequestException;
import com.portfolio.exception.ItemNotFoundException;
import com.portfolio.exception.OrderAlreadyPaidException;
import com.portfolio.model.request.order.PaymentRequest;
import com.portfolio.model.response.order.PaymentResponse;
import com.portfolio.repository.OrderRepository;
import com.portfolio.validator.CardValidator;
import com.portfolio.validator.OrderRequestValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class PaymentServiceTest {

    @Mock
    private PaymentRequest paymentRequest;
    @Mock
    private PaymentResponse paymentResponse;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private CardValidator cardValidator;
    @Mock
    private OrderRequestValidator orderRequestValidator;
    private PaymentService paymentService;


    @BeforeEach
    void setup() {
        paymentService = new PaymentServiceImpl(orderRepository, cardValidator, orderRequestValidator);

        when(paymentResponse.getOrderId()).thenReturn(1);
        when(paymentResponse.isPaid()).thenReturn(true);
        when(paymentRequest.getCardNumber()).thenReturn("5299121004475672");
        when(paymentRequest.getCardCVV()).thenReturn("747");
        when(paymentRequest.getYear()).thenReturn(2024);
        when(paymentRequest.getMonth()).thenReturn(5);

        Order order = OrderTemplate.createOrder();
        when(orderRepository.findById(1)).thenReturn(Optional.of(order));
        when(orderRepository.save(order)).thenReturn(order);
    }

    @Test
    void finishPurchase() {
        PaymentResponse response = paymentService.finishPurchase(1, paymentRequest);
        assertNotNull(response);
        assertEquals(paymentResponse.getOrderId(), response.getOrderId());
        assertEquals(paymentResponse.isPaid(), response.isPaid());
    }

    @Test
    void checkIfOrderExists() throws OrderAlreadyPaidException, ItemNotFoundException {
        when(orderRepository.findById(1)).thenReturn(Optional.empty());
        doThrow(ItemNotFoundException.class).when(orderRequestValidator).validateOrderByIdRequest(1);
        assertThrows(InvalidRequestException.class, () -> paymentService.finishPurchase(1, paymentRequest));
    }

    @Test
    void checkIfOrderIsPaid() throws OrderAlreadyPaidException, ItemNotFoundException {
        when(orderRepository.findById(1)).thenReturn(Optional.empty());
        doThrow(OrderAlreadyPaidException.class).when(orderRequestValidator).validateOrderByIdRequest(1);
        assertThrows(InvalidRequestException.class, () -> paymentService.finishPurchase(1, paymentRequest));
    }
}
