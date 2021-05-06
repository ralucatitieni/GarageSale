package com.portfolio.service;

import com.portfolio.model.request.order.CardDetailsRequest;
import com.portfolio.model.response.order.PaymentResponse;

public interface PaymentService {
    PaymentResponse payOrder(String orderId, CardDetailsRequest cardDetailsRequest);
}
