package com.portfolio.model.response.order;

import com.portfolio.entity.order.Order;
import com.portfolio.entity.product.Product;
import com.portfolio.enums.RequestType;
import com.portfolio.model.response.ResponseFactory;
import com.portfolio.model.response.ResponseFactoryProvider;
import com.portfolio.model.response.product.ProductResponse;

import java.util.HashSet;
import java.util.Set;

public class OrderResponseFactory implements ResponseFactory<Order, OrderResponse> {

    private ResponseFactory<Product, ProductResponse> productResponseFactory;

    public OrderResponseFactory(ResponseFactoryProvider responseFactoryProvider) {
        this.productResponseFactory = responseFactoryProvider.getFactory(RequestType.PRODUCT);
    }

    @Override
    public OrderResponse createResponse(Order order) {
        Set<ProductResponse> productResponseList = createProductResponse(order.getProducts());
        double amountToPay = order.getTotal();
        OrderResponse orderResponse = OrderResponse.builder()
                .id(order.getId())
                .productResponses(productResponseList)
                .totalAmount(amountToPay).build();
        return orderResponse;
    }


    private Set<ProductResponse> createProductResponse(Set<Product> productList) {
        Set<ProductResponse> productResponseList = new HashSet<>();
        ProductResponse productResponse;
        for (Product product : productList) {
            productResponse = productResponseFactory.createResponse(product);
            productResponse.setStock(1);
            productResponseList.add(productResponse);
        }
        return productResponseList;
    }
}
