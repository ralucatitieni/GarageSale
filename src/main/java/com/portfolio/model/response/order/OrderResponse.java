package com.portfolio.model.response.order;

import com.portfolio.enums.Currency;
import com.portfolio.model.response.Response;
import com.portfolio.model.response.product.ProductResponse;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@SuperBuilder
@Getter
public class OrderResponse extends Response {

    private Set<ProductResponse> productResponses;
    private final Currency currency = Currency.RON;
    private double totalAmount;
}
