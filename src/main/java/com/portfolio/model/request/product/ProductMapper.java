package com.portfolio.model.request.product;

import com.portfolio.enums.*;
import com.portfolio.model.product.Product;
import com.portfolio.model.product.accessory.Headphone;
import com.portfolio.model.product.accessory.Keyboard;
import com.portfolio.model.product.electronic.Laptop;
import com.portfolio.model.product.electronic.Phone;
import com.portfolio.model.purchase.Price;
import com.portfolio.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    private ProductRepository productRepository;

    @Autowired
    public ProductMapper(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product mapProduct(ProductRequest productRequest) {
        Product newProduct = null;
        switch (productRequest.getCategory().toUpperCase()) {
            case "LAPTOP":
                newProduct = createLaptop(productRequest);
                break;
            case "PHONE":
                newProduct = createPhone(productRequest);
                break;
            case "HEADPHONE":
                newProduct = createHeadphone(productRequest);
                break;
            case "KEYBOARD":
                newProduct = createKeyboard(productRequest);
                break;
        }
        for (Product product : productRepository.getAllProducts()) {
            if (product.equals(newProduct)) {
                newProduct = product;
            }
        }
        return newProduct;
    }

    private Product createLaptop(ProductRequest productRequest) {
        Product newProduct = Laptop.builder()
                .price(new Price(Currency.valueOf(productRequest.getCurrency()), productRequest.getAmount()))
                .issue(productRequest.getIssue())
                .productCategory(ProductCategory.LAPTOP)
                .operatingSystem(LaptopOperatingSystem.valueOf(productRequest.getLaptopRequest().getOperatingSystem()))
                .memorySize(MemorySize.valueOf("RAM" + productRequest.getLaptopRequest().getMemorySize() + "GB"))
                .diagonalDisplay(productRequest.getLaptopRequest().getDiagonalDisplay())
                .laptopBrand(productRequest.getLaptopRequest().getBrand())
                .laptopModel(productRequest.getLaptopRequest().getModel())
                .build();
        return newProduct;
    }

    private Product createPhone(ProductRequest productRequest) {
        Product newProduct = Phone.builder()
                .price(new Price(Currency.valueOf(productRequest.getCurrency()), productRequest.getAmount()))
                .issue(productRequest.getIssue())
                .productCategory(ProductCategory.PHONE)
                .operatingSystem(PhoneOperatingSystem.valueOf(productRequest.getPhoneRequest().getOperatingSystem()))
                .memorySize(MemorySize.valueOf("RAM" + productRequest.getPhoneRequest().getMemorySize() + "GB"))
                .phoneBrand(PhoneBrand.valueOf(productRequest.getPhoneRequest().getBrand()))
                .phoneModel(productRequest.getPhoneRequest().getModel())
                .dualSim(productRequest.getPhoneRequest().getDualSim())
                .build();
        return newProduct;
    }

    private Product createHeadphone(ProductRequest productRequest) {
        Product newProduct = Headphone.builder()
                .price(new Price(Currency.valueOf(productRequest.getCurrency()), productRequest.getAmount())).
                        issue(productRequest.getIssue()).
                        productCategory(ProductCategory.HEADPHONE).
                        headphoneBrand(productRequest.getHeadphoneRequest().getBrand()).
                        headphoneType(productRequest.getHeadphoneRequest().getType()).
                        build();
        return newProduct;
    }

    private Product createKeyboard(ProductRequest productRequest) {
        Product newProduct = Keyboard.builder()
                .price(new Price(Currency.valueOf(productRequest.getCurrency()), productRequest.getAmount())).
                        issue(productRequest.getIssue()).
                        productCategory(ProductCategory.KEYBOARD).
                        keyboardBrand(productRequest.getKeyboardRequest().getBrand()).
                        keyboardType(productRequest.getKeyboardRequest().getType()).
                        build();
        return newProduct;
    }
}