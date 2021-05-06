package com.portfolio.service;

import com.portfolio.model.product.Product;
import com.portfolio.model.product.accessory.Headphone;
import com.portfolio.model.product.accessory.Keyboard;
import com.portfolio.model.product.electronic.Laptop;
import com.portfolio.model.product.electronic.Phone;
import com.portfolio.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@Component
public class ProductFilter {

    private ProductRepository productRepository;

    @Autowired
    public ProductFilter(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product returnProduct(String id) {
        return productRepository.getAllProducts().stream()
                .filter(p -> p.getId().equalsIgnoreCase(id))
                .findFirst().orElse(null);
    }

    public List<Laptop> getAllLaptops() {
        return productRepository.getAllProducts().stream()
                .filter(p -> p instanceof Laptop).map(p -> (Laptop) p)
                .collect(Collectors.toList());
    }

    public List<Phone> getAllPhones() {
        return productRepository.getAllProducts().stream()
                .filter(p -> p instanceof Phone).map(p -> (Phone) p)
                .collect(Collectors.toList());
    }

    public List<Keyboard> getAllKeyboards() {
        return productRepository.getAllProducts().stream()
                .filter(p -> p instanceof Keyboard).map(p -> (Keyboard) p)
                .collect(Collectors.toList());
    }

    public List<Headphone> getAllHeadphones() {
        return productRepository.getAllProducts().stream()
                .filter(p -> p instanceof Headphone).map(p -> (Headphone) p)
                .collect(Collectors.toList());
    }

    public List<Product> sortByPrice() {
        List<Product> sortedByPrice = new ArrayList<>(productRepository.getAllProducts());
        Collections.sort(sortedByPrice, Comparator.comparingDouble(p -> p.getPrice().getAmount()));
        return sortedByPrice;
    }

    public List<Product> filterByCategory(String categoryOption) {
        return productRepository.getAllProducts().stream().filter(p -> p.getProductCategory().
                toString().equalsIgnoreCase(categoryOption)).collect(Collectors.toList());
    }

    public List<Laptop> filterLaptopByBrand(String brandOption) {
        return getAllLaptops().stream().
                filter(l -> l.getLaptopBrand().equalsIgnoreCase(brandOption)).collect(Collectors.toList());
    }

    public List<Laptop> filterLaptopByModel(String model) {
        return getAllLaptops().stream().
                filter(l -> l.getLaptopModel().equalsIgnoreCase(model)).collect(Collectors.toList());
    }


    public List<Laptop> filterLaptopByOperatingSystem(String opSystem) {
        return getAllLaptops().stream().
                filter(l -> l.getLaptopModel().equalsIgnoreCase(opSystem)).collect(Collectors.toList());
    }

    public List<Laptop> filterLaptopByDiagonal(Integer minDiag, Integer maxDiag) {
        return getAllLaptops().stream().
                filter(p -> p.getDiagonalDisplay() >= minDiag && p.getDiagonalDisplay() <= maxDiag)
                .collect(Collectors.toList());

    }

    public List<Phone> filterPhoneByBrand(String brand) {
        return getAllPhones().stream().filter(p -> p.getPhoneBrand().toString().equalsIgnoreCase(brand))
                .collect(Collectors.toList());

    }

    public List<Phone> filterPhoneByModel(String model) {
        return getAllPhones().stream().filter(p -> p.getPhoneModel().equalsIgnoreCase(model))
                .collect(Collectors.toList());

    }

    public List<Keyboard> filterKeyboardByBrand(String brand) {
        return getAllKeyboards().stream().filter(p -> p.getKeyboardBrand().equalsIgnoreCase(brand))
                .collect(Collectors.toList());
    }


    public List<Headphone> filterHeadphoneByBrand(String brand) {
        return getAllHeadphones().stream().filter(p -> p.getHeadphoneBrand().equalsIgnoreCase(brand))
                .collect(Collectors.toList());
    }


}

