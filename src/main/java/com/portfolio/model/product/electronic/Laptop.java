package com.portfolio.model.product.electronic;

import com.portfolio.enums.LaptopOperatingSystem;
import com.portfolio.enums.MemorySize;
import com.portfolio.enums.ProductCategory;
import com.portfolio.model.product.Product;
import com.portfolio.model.purchase.Price;
import lombok.Getter;

@Getter
public class Laptop extends Product {

    private static final double CURRENCY_CONVERTER_EUR = 5;
    private static final double CURRENCY_CONVERTER_USD = 4.5;
    private final LaptopOperatingSystem operatingSystem;
    private final MemorySize memorySize;
    private final int diagonalDisplay;
    private final String laptopBrand;
    private final String laptopModel;

    public Laptop(Price price, String issue, ProductCategory productCategory, LaptopOperatingSystem operatingSystem, MemorySize memorySize,
                  int diagonalDisplay, String laptopBrand, String laptopModel) {
        super(price, issue, productCategory);
        this.operatingSystem = operatingSystem;
        this.memorySize = memorySize;
        this.diagonalDisplay = diagonalDisplay;
        this.laptopBrand = laptopBrand;
        this.laptopModel = laptopModel;
    }

    @Override
    public String toString() {
        return "LAPTOP " + '\n' +
                " Brand: " + laptopBrand +
                ", Model: " + laptopModel + '\'' +
                ", Operating System: " + operatingSystem +
                ", Memory Size: " + memorySize +
                ", Diagonal Display: " + diagonalDisplay +
                ", Price: " + getPrice().getAmount() + "RON" +
                ", Issue: " + getIssue() + '\n' +
                " Product ID " + getId();
    }
}