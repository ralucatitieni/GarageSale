package com.portfolio.model.product.accessory;

import com.portfolio.model.product.Product;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.Objects;

@Getter
@SuperBuilder
public class Headphone extends Product {

    private String headphoneBrand;
    private String headphoneType;

    @Override
    public String toString() {
        return "HEADPHONE " + '\n' +
                " Brand: " + headphoneBrand +
                ", Headphone Type: " + headphoneType +
                ", Price: " + getPrice().getAmount() + " " + getPrice().getCurrency() +
                ", Issue: " + getIssue() + '\n' +
                " Product ID: " + getId();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Headphone headphone = (Headphone) o;
        return Objects.equals(headphoneBrand, headphone.headphoneBrand) && Objects.equals(headphoneType, headphone.headphoneType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(headphoneBrand, headphoneType);
    }
}
