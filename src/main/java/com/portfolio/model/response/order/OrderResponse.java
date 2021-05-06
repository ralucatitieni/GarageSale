package com.portfolio.model.response.order;


import com.portfolio.enums.Currency;
import com.portfolio.model.response.product.ProductResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderResponse {

    private final Currency currency = Currency.RON;
    private String orderId;
    private List<ProductResponse> productResponses;
    private double totalAmount;
}
