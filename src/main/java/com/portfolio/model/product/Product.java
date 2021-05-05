package com.portfolio.model.product;

import com.portfolio.enums.ProductCategory;
import com.portfolio.model.purchase.Price;
import lombok.Getter;

import java.util.UUID;

@Getter
public abstract class Product {

    private final String id;
    private final ProductCategory productCategory;
    private final Price price;
    private final String issue;

    public Product(Price price, String issue, ProductCategory productCategory) {
        this.price = price;
        this.issue = issue;
        this.productCategory = productCategory;
        this.id = UUID.randomUUID().toString().substring(0, 8);
    }
}