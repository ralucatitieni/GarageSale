package com.portfolio.repository;

import com.portfolio.enums.*;
import com.portfolio.model.product.Product;
import com.portfolio.model.product.accessory.Headphone;
import com.portfolio.model.product.accessory.Keyboard;
import com.portfolio.model.product.electronic.Laptop;
import com.portfolio.model.product.electronic.Phone;
import com.portfolio.model.purchase.Price;

import java.util.ArrayList;
import java.util.List;

public class ProductRepository {

    public static List<Product> getAllProducts() {
        List<Product> productList = new ArrayList<>();

        Product product1 = new Laptop(new Price(Currency.USD, 800), "1", ProductCategory.LAPTOP, LaptopOperatingSystem.LINUX, MemorySize.RAM32GB,
                24, "LENOVO", "Lenovo1");
        Product product2 = new Phone(new Price(Currency.USD, 300), "2", ProductCategory.PHONE, PhoneOperatingSystem.ANDROID,
                MemorySize.RAM8GB, PhoneBrand.SAMSUNG, "Samsung1", true);
        Product product3 = new Headphone(new Price(Currency.RON, 200), "2", ProductCategory.HEADPHONE, "Skullcandy", "in-ear");
        Product product4 = new Keyboard(new Price(Currency.RON, 60), "4", ProductCategory.KEYBOARD, "Logitech", "wireless");
        Product product5 = new Laptop(new Price(Currency.EUR, 500), "5", ProductCategory.LAPTOP, LaptopOperatingSystem.WINDOWS, MemorySize.RAM16GB,
                24, "ASUS", "Asus1");
        Product product6 = new Laptop(new Price(Currency.RON, 800), "6", ProductCategory.LAPTOP, LaptopOperatingSystem.WINDOWS, MemorySize.RAM8GB,
                15, "ASUS", "Asus2");
        Product product7 = new Laptop(new Price(Currency.USD, 1000), "7", ProductCategory.LAPTOP, LaptopOperatingSystem.WINDOWS, MemorySize.RAM32GB,
                17, "DELL", "Dell1");

        productList.add(product1);
        productList.add(product2);
        productList.add(product3);
        productList.add(product4);
        productList.add(product5);
        productList.add(product6);
        productList.add(product7);

        return productList;
    }
}

