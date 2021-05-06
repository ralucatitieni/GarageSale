package com.portfolio.model.product;

import lombok.Getter;
import lombok.Setter;

import java.util.Random;

@Getter
@Setter
public class Stock {

    private final Random random = new Random();
    private String productId;
    private int numberOfItems;

    public Stock(String productId) {
        this.productId = productId;
        this.numberOfItems = random.nextInt(5);
    }

    @Override
    public String toString() {
        return " NumberOfItems: " + numberOfItems;
    }
}
