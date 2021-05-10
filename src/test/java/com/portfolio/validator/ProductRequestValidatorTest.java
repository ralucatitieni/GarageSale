package com.portfolio.validator;

import com.portfolio.exception.InvalidProductRequestException;
import com.portfolio.exception.ItemNotFoundException;
import com.portfolio.model.request.product.LaptopRequest;
import com.portfolio.model.request.product.PhoneRequest;
import com.portfolio.model.request.product.ProductRequest;
import com.portfolio.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class ProductRequestValidatorTest {

    public static final int ID = 5;
    @Mock
    private ProductRequest productRequest;
    @Mock
    private LaptopRequest laptopRequest;
    @Mock
    private PhoneRequest phoneRequest;
    @Mock
    private ProductRepository productRepository;
    private ProductRequestValidator productRequestValidator;


    @BeforeEach
    public void setup() {
        productRequestValidator = new ProductRequestValidator(productRepository);
        when(productRequest.getStock()).thenReturn(5);
        when(productRequest.getAmount()).thenReturn(50.0);
        when(productRequest.getCategory()).thenReturn("LAPTOP");
        when(productRequest.getCurrency()).thenReturn("EUR");
        when(productRepository.findById(ID)).thenReturn(Optional.empty());
    }

    @Test
    void checkEmptyOrNegativeStock() {
        when(productRequest.getStock()).thenReturn(-5);
        // headphoneRequest.setStock(-5);
        Exception exception = assertThrows(InvalidProductRequestException.class, () -> productRequestValidator.validateProductRequest(productRequest));
        assertEquals("Cannot add zero or negative value for the stock", exception.getMessage());
    }

    @Test
    void checkEmptyOrNegativeAmount() {
        when(productRequest.getAmount()).thenReturn(-50.0);
        Exception exception = assertThrows(InvalidProductRequestException.class, () -> productRequestValidator.validateProductRequest(productRequest));
        assertEquals("Cannot add zero or negative value for the price", exception.getMessage());
    }

    @Test
    void checkNonexistentCategory() {
        when(productRequest.getCategory()).thenReturn("HANDBAG");
        Exception exception = assertThrows(InvalidProductRequestException.class, () -> productRequestValidator.validateProductRequest(productRequest));
        assertEquals("Product category not available", exception.getMessage());
    }

    @Test
    void checkNonexistentCurrency() {
        when(productRequest.getCurrency()).thenReturn("INR");
        Exception exception = assertThrows(InvalidProductRequestException.class, () -> productRequestValidator.validateProductRequest(productRequest));
        assertEquals("Currency not available", exception.getMessage());
    }

    @Test
    void checkLessThanZeroLaptopDiagonalDisplay() {
        when(productRequest.getLaptopRequest()).thenReturn(laptopRequest);
        when(laptopRequest.getDiagonalDisplay()).thenReturn(-21);
        Exception exception = assertThrows(InvalidProductRequestException.class, () -> productRequestValidator.validateProductRequest(productRequest));
        assertEquals("Cannot add zero or negative value for the diagonal display", exception.getMessage());
    }

    @Test
    void checkNonexistentLaptopOperatingSystem() {
        when(productRequest.getLaptopRequest()).thenReturn(laptopRequest);
        when(laptopRequest.getDiagonalDisplay()).thenReturn(21);
        when(laptopRequest.getOperatingSystem()).thenReturn("SOMETHING");
        Exception exception = assertThrows(ItemNotFoundException.class, () -> productRequestValidator.validateProductRequest(productRequest));
        assertEquals("Operating System not available", exception.getMessage());
    }

    @Test
    void checkNonexistentLaptopMemorySize() {
        when(productRequest.getLaptopRequest()).thenReturn(laptopRequest);
        when(laptopRequest.getDiagonalDisplay()).thenReturn(21);
        when(laptopRequest.getOperatingSystem()).thenReturn("WINDOWS");
        when(laptopRequest.getMemorySize()).thenReturn(40);
        Exception exception = assertThrows(ItemNotFoundException.class, () -> productRequestValidator.validateProductRequest(productRequest));
        assertEquals("Memory size not available", exception.getMessage());
    }

    @Test
    void checkNonexistentPhoneOperatingSystem() {
        when(productRequest.getCategory()).thenReturn("PHONE");
        when(productRequest.getPhoneRequest()).thenReturn(phoneRequest);
        when(phoneRequest.getOperatingSystem()).thenReturn("SOMETHING");
        Exception exception = assertThrows(ItemNotFoundException.class, () -> productRequestValidator.validateProductRequest(productRequest));
        assertEquals("Operating System not available", exception.getMessage());
    }

    @Test
    void checkNonexistentPhoneMemorySize() {
        when(productRequest.getCategory()).thenReturn("PHONE");
        when(productRequest.getPhoneRequest()).thenReturn(phoneRequest);
        when(phoneRequest.getOperatingSystem()).thenReturn("ANDROID");
        when(phoneRequest.getMemorySize()).thenReturn(5);

        Exception exception = assertThrows(ItemNotFoundException.class, () -> productRequestValidator.validateProductRequest(productRequest));
        assertEquals("Memory size not available", exception.getMessage());
    }

    @Test
    void checkNonexistentPhoneBrand() {
        when(productRequest.getCategory()).thenReturn("PHONE");
        when(productRequest.getPhoneRequest()).thenReturn(phoneRequest);
        when(phoneRequest.getOperatingSystem()).thenReturn("ANDROID");
        when(phoneRequest.getMemorySize()).thenReturn(4);
        when(phoneRequest.getBrand()).thenReturn("MOTOROLA");
        Exception exception = assertThrows(ItemNotFoundException.class, () -> productRequestValidator.validateProductRequest(productRequest));
        assertEquals("Phone brand not available", exception.getMessage());
    }

    @Test
    void checkIfProductsExistsInRepository() {
        Exception exception = assertThrows(ItemNotFoundException.class, () -> productRequestValidator.validateProductByIdRequest(5));
        assertEquals("Product with id " + 5 + " not found", exception.getMessage());
    }
}