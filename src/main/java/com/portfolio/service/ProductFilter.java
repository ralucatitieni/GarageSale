package com.portfolio.service;

import com.portfolio.model.product.Product;
import com.portfolio.model.product.accessory.Headphone;
import com.portfolio.model.product.accessory.Keyboard;
import com.portfolio.model.product.electronic.Laptop;
import com.portfolio.model.product.electronic.Phone;

import java.util.function.Predicate;

public class ProductFilter {

    public static Predicate<Product> filterByCategory(String category) {
        return p -> p.getProductCategory().toString().equalsIgnoreCase(category);
    }

    public static Predicate<Laptop> filterLaptopByBrand(String brand) {
        return p -> p.getLaptopBrand().equalsIgnoreCase(brand);
    }

    public static Predicate<Laptop> filterLaptopByModel(String model) {
        return p -> p.getLaptopModel().equalsIgnoreCase(model);
    }

    public static Predicate<Laptop> filterLaptopByOperatingSystem(String opSystem) {
        return p -> p.getOperatingSystem().toString().equalsIgnoreCase(opSystem);
    }

    public static Predicate<Laptop> filterLaptopByDiagonal(Integer minDiag, Integer maxDiag) {
        return p -> p.getDiagonalDisplay() >= minDiag && p.getDiagonalDisplay() <= maxDiag;
    }

    public static Predicate<Phone> filterPhoneByBrand(String brand) {
        return p -> p.getPhoneBrand().toString().equalsIgnoreCase(brand);
    }

    public static Predicate<Phone> filterPhoneByModel(String model) {
        return p -> p.getPhoneModel().equalsIgnoreCase(model);
    }

    public static Predicate<Keyboard> filterKeyboardByBrand(String brand) {
        return p -> p.getKeyboardBrand().equalsIgnoreCase(brand);
    }

    public static Predicate<Headphone> filterHeadphoneByBrand(String brand) {
        return p -> p.getHeadphoneType().equalsIgnoreCase(brand);
    }


}

