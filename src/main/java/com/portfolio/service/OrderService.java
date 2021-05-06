package com.portfolio.service;

import com.portfolio.model.request.order.OrderRequest;
import com.portfolio.model.response.order.OrderResponse;

public interface OrderService {

    OrderResponse placeOrder(OrderRequest orderRequest);
}
