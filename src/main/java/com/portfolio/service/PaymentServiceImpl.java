package com.portfolio.service;

import com.portfolio.exception.InvalidCardException;
import com.portfolio.exception.InvalidRequestException;
import com.portfolio.exception.ItemNotFoundException;
import com.portfolio.model.purchase.Card;
import com.portfolio.model.purchase.Order;
import com.portfolio.model.request.order.CardDetailsRequest;
import com.portfolio.model.response.order.PaymentResponse;
import com.portfolio.repository.OrderRepository;
import com.portfolio.validator.CardValidator;
import com.portfolio.validator.OrderRequestValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.YearMonth;

@Component
public class PaymentServiceImpl implements PaymentService {

    private OrderRepository orderRepository;
    private CardValidator cardValidator;
    private OrderRequestValidator orderRequestValidator;

    @Autowired
    public PaymentServiceImpl(OrderRepository orderRepository, CardValidator cardValidator, OrderRequestValidator orderRequestValidator) {
        this.orderRepository = orderRepository;
        this.cardValidator = cardValidator;
        this.orderRequestValidator = orderRequestValidator;
    }

    @Override
    public PaymentResponse payOrder(String orderId, CardDetailsRequest cardDetailsRequest) {
        Order order = addOrderDetails(orderId, cardDetailsRequest);
        PaymentResponse paymentResponse = new PaymentResponse();
        paymentResponse.setOrderId(order.getId());
        paymentResponse.setPaid(order.isPaid());
        return paymentResponse;
    }

    private Card createCard(CardDetailsRequest cardDetailsRequest) {
        YearMonth expireDate = YearMonth.of(cardDetailsRequest.getYear(), cardDetailsRequest.getMonth());
        Card card = new Card(cardDetailsRequest.getCardNumber(), cardDetailsRequest.getCardCVV(), expireDate);
        return card;
    }

    private Order addOrderDetails(String orderId, CardDetailsRequest cardDetailsRequest) {
        Card card = createCard(cardDetailsRequest);
        try {
            cardValidator.validateCard(card);
        } catch (InvalidCardException e) {
            throw new InvalidRequestException(e.getMessage(), e);
        }
        Order order;
        try {
            orderRequestValidator.validateOrderByIdRequest(orderId);
            order = orderRepository.getOrderById(orderId);
        } catch (ItemNotFoundException e) {
            throw new InvalidRequestException(e.getMessage(), e);
        }
        order.setCard(card);
        order.setPaid(true);
        return order;
    }
}

