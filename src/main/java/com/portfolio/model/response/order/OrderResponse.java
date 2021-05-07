package com.portfolio.model.response.order;

import com.portfolio.enums.Currency;
import com.portfolio.model.response.product.ProductResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class OrderResponse {

    private final Currency currency = Currency.RON;
    private int orderId;
    private double totalAmount;
    private Set<ProductResponse> productResponses;
}