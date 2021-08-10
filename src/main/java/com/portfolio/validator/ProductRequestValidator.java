package com.portfolio.validator;

import com.portfolio.enums.*;
import com.portfolio.exception.InvalidProductRequestException;
import com.portfolio.exception.ItemNotFoundException;
import com.portfolio.model.request.product.ProductRequest;
import com.portfolio.repository.ProductRepository;
import org.springframework.stereotype.Component;


@Component
public class ProductRequestValidator {

    private ProductRepository productRepository;

    public ProductRequestValidator(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void validateProductRequest(ProductRequest productRequest) throws ItemNotFoundException, InvalidProductRequestException {
        if (productRequest.getStock() <= 0) {
            throw new InvalidProductRequestException("Cannot add zero or negative value for the stock");
        }
        if (productRequest.getAmount() <= 0) {
            throw new InvalidProductRequestException("Cannot add zero or negative value for the price");
        }
        if (ProductCategory.getProductCategory(productRequest.getCategory()) == null) {
            throw new InvalidProductRequestException("Product category not available");
        }
        if (Currency.getCurrency(productRequest.getCurrency()) == null) {
            throw new InvalidProductRequestException("Currency not available");
        }
        switch (productRequest.getCategory().toUpperCase()) {
            case "LAPTOP":
                if (productRequest.getLaptopRequest().getDiagonalDisplay() <= 0) {
                    throw new InvalidProductRequestException("Cannot add zero or negative value for the diagonal display");
                }
                if (LaptopOperatingSystem.getLaptopOperatingSystem(productRequest.getLaptopRequest().getOperatingSystem()) == null) {
                    throw new ItemNotFoundException("Operating System not available");
                }
                if (MemorySize.getMemorySize(productRequest.getLaptopRequest().getMemorySize()) == null) {
                    throw new ItemNotFoundException("Memory size not available");
                }
                break;
            case "PHONE":
                if (PhoneOperatingSystem.getPhoneOperatingSystem(productRequest.getPhoneRequest().getOperatingSystem()) == null) {
                    throw new ItemNotFoundException("Operating System not available");
                }
                if (MemorySize.getMemorySize(productRequest.getPhoneRequest().getMemorySize()) == null) {
                    throw new ItemNotFoundException("Memory size not available");
                }
                if (PhoneBrand.getPhoneBrand(productRequest.getPhoneRequest().getBrand()) == null) {
                    throw new ItemNotFoundException("Phone brand not available");
                }
                break;
        }
    }

    public void validateProductByIdRequest(int id) throws ItemNotFoundException {
        if (productRepository.findById(id).isEmpty()) {
            throw new ItemNotFoundException("Product with id " + id + " not found");
        }
    }
}