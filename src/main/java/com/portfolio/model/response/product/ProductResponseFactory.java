package com.portfolio.model.response.product;

import com.portfolio.entity.product.Product;
import com.portfolio.entity.product.ProductDetail;
import com.portfolio.enums.Currency;
import com.portfolio.model.purchase.Price;
import com.portfolio.model.response.ResponseFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ProductResponseFactory implements ResponseFactory<Product, ProductResponse> {

    @Override
    public ProductResponse createResponse(Product product) {
        ProductResponse productResponse = ProductResponse.builder()
                .id(product.getId())
                .category(product.getCategory())
                .price(new Price(Currency.valueOf(product.getCurrency()), product.getAmount()))
                .issues(createIssues(product))
                .characteristics(createCharacteristics(product))
                .stock(product.getStock().getNumberOfItems()).build();
        return productResponse;
    }

    private Map<String, String> createCharacteristics(Product product) {
        Map<String, String> characteristics = new TreeMap<>();
        for (ProductDetail productDetail : product.getDetails()) {
            characteristics.put(productDetail.getName(), productDetail.getValue());
        }
        return characteristics;
    }

    private List<String> createIssues(Product product) {
        String[] arrayOfIssues = product.getIssue().split("/");
        List<String> issues = Arrays.asList(arrayOfIssues);
        return issues;
    }
}