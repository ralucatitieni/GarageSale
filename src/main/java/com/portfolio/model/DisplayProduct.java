package com.portfolio.model;

import com.portfolio.model.product.Product;
import com.portfolio.model.product.Stock;

public class DisplayProduct {

    public Product product;
    public Stock stock;

    public DisplayProduct(Product product, Stock stock) {
        this.product = product;
        this.stock = stock;
    }

    @Override
    public String toString() {
        return product + " " + stock;

    }
}