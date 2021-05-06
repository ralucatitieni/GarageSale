package com.portfolio.service;

import com.portfolio.model.request.product.ProductRequest;
import com.portfolio.model.response.product.ProductResponse;

import java.util.List;

public interface ProductService {
    ProductResponse addProduct(ProductRequest productRequest);

    List<ProductResponse> getProductResponses();

    ProductResponse getProductResponseById(String id);
}
