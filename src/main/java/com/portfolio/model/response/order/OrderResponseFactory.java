package com.portfolio.model.response.order;


import com.portfolio.enums.Currency;
import com.portfolio.model.product.Product;
import com.portfolio.model.purchase.Order;
import com.portfolio.model.response.product.ProductResponse;
import com.portfolio.model.response.product.ProductResponseFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderResponseFactory {

    private static final double CURRENCY_CONVERTER_EUR = 5;
    private static final double CURRENCY_CONVERTER_USD = 4.5;
    private ProductResponseFactory productResponseFactory;


    @Autowired
    public OrderResponseFactory(ProductResponseFactory productResponseFactory) {
        this.productResponseFactory = productResponseFactory;
    }

    public OrderResponse createOrderResponse(Order order) {
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setOrderId(order.getId());
        List<ProductResponse> productResponseList = createProductResponse(order.getItemsInCart());
        orderResponse.setProductResponses(productResponseList);
        double amountTopPay = getTotalAmountInRON(order.getItemsInCart());
        orderResponse.setTotalAmount(amountTopPay);
        return orderResponse;
    }

    private double getTotalAmountInRON(List<Product> productList) {
        double amountInRON = 0;
        for (Product product : productList) {
            if (product.getPrice().getCurrency().equals(Currency.RON)) {
                amountInRON += (product.getPrice().getAmount());
            } else if (product.getPrice().getCurrency().equals(Currency.EUR)) {
                amountInRON += (product.getPrice().getAmount() * CURRENCY_CONVERTER_EUR);
            } else if (product.getPrice().getCurrency().equals(Currency.USD)) {
                amountInRON += (product.getPrice().getAmount() * CURRENCY_CONVERTER_USD);
            }
        }
        return amountInRON;
    }

    private List<ProductResponse> createProductResponse(List<Product> productList) {
        List<ProductResponse> productResponseList = new ArrayList<>();
        ProductResponse productResponse;
        for (Product product : productList) {
            productResponse = productResponseFactory.createProductResponse(product, 1);
            productResponseList.add(productResponse);
        }
        return productResponseList;
    }
}
