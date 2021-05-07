package com.portfolio.service;

import com.portfolio.entity.order.Order;
import com.portfolio.exception.InvalidCardException;
import com.portfolio.exception.InvalidRequestException;
import com.portfolio.exception.ItemNotFoundException;
import com.portfolio.exception.OrderAlreadyPaidException;
import com.portfolio.model.purchase.Card;
import com.portfolio.model.request.order.PaymentRequest;
import com.portfolio.model.response.order.PaymentResponse;
import com.portfolio.repository.OrderRepository;
import com.portfolio.validator.CardValidator;
import com.portfolio.validator.OrderRequestValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.YearMonth;

@Component
public class PaymentServiceImpl implements PaymentService {

    private final Logger logger = LoggerFactory.getLogger(PaymentServiceImpl.class);
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
    public PaymentResponse finishPurchase(int orderId, PaymentRequest paymentRequest) {
        Order order = addOrderDetails(orderId, paymentRequest);
        PaymentResponse paymentResponse = new PaymentResponse();
        paymentResponse.setOrderId(order.getId());
        paymentResponse.setPaid(order.isPaid());
        return paymentResponse;
    }

    private Card createCard(PaymentRequest paymentRequest) {
        YearMonth expireDate = YearMonth.of(paymentRequest.getYear(), paymentRequest.getMonth());
        Card card = new Card(paymentRequest.getCardNumber(), paymentRequest.getCardCVV(), expireDate);
        return card;
    }

    private Order addOrderDetails(int orderId, PaymentRequest paymentRequest) {
        Card card = createCard(paymentRequest);
        try {
            cardValidator.validateCard(card);
            logger.info("Card is valid");
        } catch (InvalidCardException e) {
            logger.error(e.getMessage());
            throw new InvalidRequestException(e.getMessage(), e);
        }
        Order order;
        try {
            orderRequestValidator.validateOrderByIdRequest(orderId);
            order = orderRepository.findById(orderId).get();
        } catch (ItemNotFoundException e) {
            logger.error(e.getMessage());
            throw new InvalidRequestException(e.getMessage(), e);
        } catch (OrderAlreadyPaidException e) {
            logger.error(e.getMessage());
            throw new InvalidRequestException(e.getMessage(), e);
        }
        order.setPaid(true);
        logger.info("Order with ID " + order.getId() + " is paid.");
        orderRepository.save(order);
        return order;
    }
}
