package com.portfolio.model.product;

import com.portfolio.enums.ProductCategory;
import com.portfolio.model.purchase.Price;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Getter
@SuperBuilder
public class Product {

    private final String id = UUID.randomUUID().toString().substring(0, 8);
    private ProductCategory productCategory;
    private Price price;
    private String issue;

}