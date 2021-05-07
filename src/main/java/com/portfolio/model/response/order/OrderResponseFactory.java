package com.portfolio.model.response.order;

import com.portfolio.model.response.product.ProductResponse;
import com.portfolio.model.response.product.ProductResponseFactory;
import com.portfolio.entity.order.Order;
import com.portfolio.entity.product.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class OrderResponseFactory {


    private ProductResponseFactory productResponseFactory;


    @Autowired
    public OrderResponseFactory(ProductResponseFactory productResponseFactory) {
        this.productResponseFactory = productResponseFactory;
    }

    public OrderResponse createOrderResponse(Order order) {
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setOrderId(order.getId());
        Set<ProductResponse> productResponseList = createProductResponse(order.getProducts());
        orderResponse.setProductResponses(productResponseList);
        double amountTopPay = order.getTotal();
        orderResponse.setTotalAmount(amountTopPay);
        return orderResponse;
    }


    private Set<ProductResponse> createProductResponse(Set<Product> productList) {
        Set<ProductResponse> productResponseList = new HashSet<>();
        ProductResponse productResponse;
        for (Product product : productList) {
            productResponse = productResponseFactory.createProductResponse(product);
            productResponse.setStock(1);
            productResponseList.add(productResponse);
        }
        return productResponseList;
    }
}