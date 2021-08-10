package com.portfolio.model.product.accessory;

import com.portfolio.model.product.Product;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.Objects;

@Getter
@SuperBuilder
public class Keyboard extends Product {

    private String keyboardBrand;
    private String keyboardType;

    @Override
    public String toString() {
        return "KEYBOARD " + '\n' +
                " Brand: " + keyboardBrand +
                ", Keyboard Type: " + keyboardType +
                ", Price: " + getPrice().getAmount() + " " + getPrice().getCurrency() +
                ", Issue: " + getIssue() + '\n' +
                " Product ID: " + getId();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Keyboard keyboard = (Keyboard) o;
        return Objects.equals(keyboardBrand, keyboard.keyboardBrand) && Objects.equals(keyboardType, keyboard.keyboardType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(keyboardBrand, keyboardType);
    }
}