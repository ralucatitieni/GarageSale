package com.portfolio.model.product.accessory;

import com.portfolio.enums.ProductCategory;
import com.portfolio.model.product.Product;
import com.portfolio.model.purchase.Price;
import lombok.Getter;

@Getter
public class Headphone extends Product {

    private final String headphoneBrand;
    private final String headphoneType;

    public Headphone(Price price, String issue, ProductCategory productCategory, String brand, String headphoneType) {
        super(price, issue, productCategory);
        this.headphoneBrand = brand;
        this.headphoneType = headphoneType;
    }

    @Override
    public String toString() {
        return "HEADPHONE " + '\n' +
                " Brand: " + headphoneBrand +
                ", Headphone Type: " + headphoneType +
                ", Price: " + getPrice().getAmount() + "RON" +
                ", Issue: " + getIssue() + '\n' +
                " Product ID " + getId();
    }
}
