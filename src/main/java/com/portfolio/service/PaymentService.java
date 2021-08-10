package com.portfolio.service;

import com.portfolio.model.request.order.PaymentRequest;
import com.portfolio.model.response.order.PaymentResponse;

public interface PaymentService {

    PaymentResponse finishPurchase(int orderId, PaymentRequest paymentRequest);
}

