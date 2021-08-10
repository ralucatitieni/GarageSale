package com.portfolio.model.response;

import com.portfolio.enums.RequestType;
import com.portfolio.model.response.order.OrderResponseFactory;
import com.portfolio.model.response.product.ProductResponseFactory;
import org.springframework.stereotype.Component;

@Component
public class ResponseFactoryProvider {

    public ResponseFactory getFactory(RequestType request) {
        switch (request) {
            case PRODUCT:
                return new ProductResponseFactory();
            case ORDER:
                return new OrderResponseFactory(this);
            default:
                return null;
        }
    }
}
