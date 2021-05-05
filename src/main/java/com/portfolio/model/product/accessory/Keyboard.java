package com.portfolio.model.product.accessory;

import com.portfolio.enums.ProductCategory;
import com.portfolio.model.product.Product;
import com.portfolio.model.purchase.Price;
import lombok.Getter;

@Getter
public class Keyboard extends Product {

    private final String keyboardBrand;
    private final String keyboardType;

    public Keyboard(Price price, String issue, ProductCategory productCategory, String keyboardBrand, String keyboardType) {
        super(price, issue, productCategory);
        this.keyboardBrand = keyboardBrand;
        this.keyboardType = keyboardType;
    }

    @Override
    public String toString() {
        return "KEYBOARD " + '\n' +
                " Brand: " + keyboardBrand +
                ", Keyboard Type: " + keyboardType +
                ", Price: " + getPrice().getAmount() + "RON" +
                ", Issue: " + getIssue() + '\n' +
                " Product ID " + getId();
    }

}