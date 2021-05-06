package com.portfolio.service;

import com.portfolio.exception.InvalidEmailException;
import com.portfolio.exception.InvalidOrderRequestException;
import com.portfolio.exception.InvalidRequestException;
import com.portfolio.model.purchase.Order;
import com.portfolio.model.request.order.OrderMapper;
import com.portfolio.model.request.order.OrderRequest;
import com.portfolio.model.response.order.OrderResponse;
import com.portfolio.model.response.order.OrderResponseFactory;
import com.portfolio.validator.OrderRequestValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderServiceImpl implements OrderService {

    private OrderResponseFactory orderResponseFactory;
    private OrderMapper orderMapper;
    private OrderRequestValidator orderRequestValidator;

    @Autowired
    public OrderServiceImpl(OrderResponseFactory orderResponseFactory, OrderMapper orderMapper, OrderRequestValidator orderRequestValidator) {
        this.orderResponseFactory = orderResponseFactory;
        this.orderMapper = orderMapper;
        this.orderRequestValidator = orderRequestValidator;
    }

    @Override
    public OrderResponse placeOrder(OrderRequest orderRequest) {
        Order order = orderMapper.createOrder(orderRequest);
        try {
            orderRequestValidator.validateProductsFromOrderRequest(order);
        } catch (InvalidOrderRequestException e) {
            throw new InvalidRequestException(e.getMessage(), e);
        } catch (InvalidEmailException e) {
            throw new InvalidRequestException(e.getMessage(), e);
        }
        orderMapper.updateStock(orderRequest.getProductIds());
        OrderResponse orderResponse = orderResponseFactory.createOrderResponse(order);
        return orderResponse;
    }
}