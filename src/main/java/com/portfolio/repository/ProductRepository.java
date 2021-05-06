package com.portfolio.repository;

import com.portfolio.enums.*;
import com.portfolio.model.product.Product;
import com.portfolio.model.product.accessory.Headphone;
import com.portfolio.model.product.accessory.Keyboard;
import com.portfolio.model.product.electronic.Laptop;
import com.portfolio.model.product.electronic.Phone;
import com.portfolio.model.purchase.Price;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Component
public class ProductRepository {

    private List<Product> productList = new ArrayList<>();

    @PostConstruct
    private void setProductList() {
        Product product1 = Laptop.builder().price(new Price(Currency.USD, 800)).
                issue("1").
                productCategory(ProductCategory.LAPTOP).
                operatingSystem(LaptopOperatingSystem.LINUX).
                memorySize(MemorySize.RAM32GB).
                diagonalDisplay(24).
                laptopBrand("LENOVO").
                laptopModel("Lenovo1").
                build();
        Product product2 = Phone.builder().price(new Price(Currency.USD, 300)).
                issue("2").
                productCategory(ProductCategory.PHONE).
                operatingSystem(PhoneOperatingSystem.ANDROID).
                memorySize(MemorySize.RAM8GB).
                phoneBrand(PhoneBrand.SAMSUNG).
                phoneModel("Samsung1").dualSim(true).
                build();
        Product product3 = Headphone.builder().price(new Price(Currency.RON, 200)).
                issue("2").
                productCategory(ProductCategory.HEADPHONE).
                headphoneBrand("Skullcandy").
                headphoneType("in-ear").
                build();
        Product product4 = Keyboard.builder().price(new Price(Currency.RON, 60)).
                issue("4").
                productCategory(ProductCategory.KEYBOARD).
                keyboardBrand("Logitech").
                keyboardType("wireless").
                build();
        Product product5 = Laptop.builder().price(new Price(Currency.EUR, 500)).
                issue("5").
                productCategory(ProductCategory.LAPTOP).
                operatingSystem(LaptopOperatingSystem.WINDOWS).
                memorySize(MemorySize.RAM16GB).
                diagonalDisplay(24).
                laptopBrand("ASUS").
                laptopModel("Asus1").
                build();
        Product product6 = Laptop.builder().price(new Price(Currency.RON, 800)).
                issue("6").
                productCategory(ProductCategory.LAPTOP).
                operatingSystem(LaptopOperatingSystem.WINDOWS).
                memorySize(MemorySize.RAM8GB).
                diagonalDisplay(15).
                laptopBrand("ASUS").
                laptopModel("Asus2").
                build();
        Product product7 = Laptop.builder().price(new Price(Currency.USD, 1000)).
                issue("7").
                productCategory(ProductCategory.LAPTOP).
                operatingSystem(LaptopOperatingSystem.WINDOWS).
                memorySize(MemorySize.RAM32GB).
                diagonalDisplay(17).
                laptopBrand("DELL").
                laptopModel("Dell1").
                build();
        Product product8 = Laptop.builder().price(new Price(Currency.USD, 600)).
                issue("8").
                productCategory(ProductCategory.LAPTOP).
                operatingSystem(LaptopOperatingSystem.LINUX).
                memorySize(MemorySize.RAM16GB).
                diagonalDisplay(20).
                laptopBrand("DELL").
                laptopModel("Dell2").
                build();
        Product product9 = Laptop.builder().price(new Price(Currency.USD, 1200)).
                issue("9").
                productCategory(ProductCategory.LAPTOP).
                operatingSystem(LaptopOperatingSystem.LINUX).
                memorySize(MemorySize.RAM32GB).
                diagonalDisplay(24).
                laptopBrand("DELL").
                laptopModel("Dell3").
                build();

        productList.addAll(Arrays.asList(product1, product2, product3, product4, product5, product6, product7
                , product8, product9));

    }

    public List<Product> getAllProducts() {
        return productList;
    }

    public void addLaptop(Price price, String issue, LaptopOperatingSystem operatingSystem,
                          MemorySize memorySize, int diagonalDisplay, String laptopBrand, String laptopModel) {
        Product newProduct = Laptop.builder().price(price).
                issue(issue).
                productCategory(ProductCategory.LAPTOP).
                operatingSystem(operatingSystem).
                memorySize(memorySize).
                diagonalDisplay(diagonalDisplay).
                laptopBrand(laptopBrand).
                laptopModel(laptopModel).
                build();
        productList.add(newProduct);
    }

    public void addPhone(Price price, String issue, PhoneOperatingSystem operatingSystem,
                         MemorySize memorySize, PhoneBrand phoneBrand, String phoneModel, boolean dualSim) {
        Product newProduct = Phone.builder().price(price).
                issue(issue).
                productCategory(ProductCategory.PHONE).
                operatingSystem(operatingSystem).
                memorySize(memorySize).
                phoneBrand(phoneBrand).
                phoneModel(phoneModel).
                dualSim(dualSim).
                build();
        productList.add(newProduct);

    }

    public void addKeyboard(Price price, String issue, String keyboardBrand, String keyboardType) {
        Product newProduct = Keyboard.builder().price(price).
                issue(issue).
                productCategory(ProductCategory.KEYBOARD).
                keyboardBrand(keyboardBrand).
                keyboardType(keyboardType).
                build();
        productList.add(newProduct);
    }

    public void addHeadphone(Price price, String issue, String headphoneBrand, String headphoneType) {
        Product newProduct = Headphone.builder().price(price).
                issue(issue).
                productCategory(ProductCategory.HEADPHONE).
                headphoneBrand(headphoneBrand).
                headphoneType(headphoneType).
                build();
        productList.add(newProduct);
    }

    public Product getProductById(String id) {
        return getAllProducts().stream()
                .filter(p -> p.getId().equalsIgnoreCase(id))
                .findFirst().orElse(null);
    }
}

