package com.portfolio.service;

import com.portfolio.model.request.product.ProductRequest;
import com.portfolio.model.request.product.StockRequest;
import com.portfolio.model.response.product.ProductResponse;

import java.util.List;

public interface ProductService {

    ProductResponse addProduct(ProductRequest productRequest);

    List<ProductResponse> getAllProducts();

    ProductResponse getProduct(int id);

    ProductResponse updateStock(int id, StockRequest stockRequest);
}
