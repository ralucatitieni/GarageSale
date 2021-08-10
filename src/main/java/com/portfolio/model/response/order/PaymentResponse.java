package com.portfolio.model.response.order;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentResponse {
    private int orderId;
    private boolean paid;
}