package com.portfolio.model.product.electronic;

import com.portfolio.enums.LaptopOperatingSystem;
import com.portfolio.enums.MemorySize;
import com.portfolio.model.product.Product;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.Objects;

@Getter
@SuperBuilder
public class Laptop extends Product {

    private static double CURRENCY_CONVERTER_EUR = 5;
    private static double CURRENCY_CONVERTER_USD = 4.5;
    private LaptopOperatingSystem operatingSystem;
    private MemorySize memorySize;
    private int diagonalDisplay;
    private String laptopBrand;
    private String laptopModel;

    @Override
    public String toString() {
        return "LAPTOP " + '\n' +
                " Brand: " + laptopBrand +
                ", Model: " + laptopModel +
                ", Operating System: " + operatingSystem +
                ", Memory Size: " + memorySize +
                ", Diagonal Display: " + diagonalDisplay +
                ", Price: " + getPrice().getAmount() + " " + getPrice().getCurrency() +
                ", Issue: " + getIssue() + '\n' +
                " Product ID: " + getId();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Laptop laptop = (Laptop) o;
        return diagonalDisplay == laptop.diagonalDisplay && operatingSystem == laptop.operatingSystem && memorySize == laptop.memorySize && Objects.equals(laptopBrand, laptop.laptopBrand) && Objects.equals(laptopModel, laptop.laptopModel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(operatingSystem, memorySize, diagonalDisplay, laptopBrand, laptopModel);
    }
}