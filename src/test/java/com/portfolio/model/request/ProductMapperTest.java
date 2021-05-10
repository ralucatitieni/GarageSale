package com.portfolio.model.request;

import com.portfolio.ProductTemplate;
import com.portfolio.entity.product.Product;
import com.portfolio.model.request.product.ProductMapper;
import com.portfolio.model.request.product.ProductRequest;
import com.portfolio.repository.ProductRepository;
import com.portfolio.repository.StockRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
class ProductMapperTest {

    @Mock
    private ProductRepository productRepository;
    @Mock
    private StockRepository stockRepository;
    private ProductMapper productMapper;
    // TODO: 5/10/2021 create mock
    private Product headphone;
    // TODO: 5/10/2021 create mock
    private Product keyboard;
    // TODO: 5/10/2021  create mock
    private Product laptop;
    // TODO: 5/10/2021  create mock
    private Product phone;
    // TODO: 5/10/2021  create mock
    private ProductRequest headphoneRequest;
    // TODO: 5/10/2021  create mock
    private ProductRequest laptopRequest;
    // TODO: 5/10/2021  create mock
    private ProductRequest phoneRequest;
    // TODO: 5/10/2021  create mock
    private ProductRequest keyboardRequest;

    @BeforeEach
    void setup() {
        productMapper = new ProductMapper(productRepository, stockRepository);

        headphone = ProductTemplate.createHeadphone();
        keyboard = ProductTemplate.createKeyboard();
        laptop = ProductTemplate.createLaptop();
        phone = ProductTemplate.createPhone();

        headphoneRequest = ProductTemplate.createHeadphoneRequest();
        keyboardRequest = ProductTemplate.createKeyboardRequest();
        laptopRequest = ProductTemplate.createLaptopRequest();
        phoneRequest = ProductTemplate.createPhoneRequest();
    }

    @Test
    void mapToHeadphone() {
        Product product = productMapper.mapToProduct(headphoneRequest);
        assertNotNull(product);
        assertEquals(headphone.getCategory(), product.getCategory());
        assertEquals(headphone.getCurrency(), product.getCurrency());
        assertEquals(headphone.getAmount(), product.getAmount());
        assertEquals(headphone.getStock(), product.getStock());
        assertEquals(headphone.getIssue(), product.getIssue());
        assertEquals(2, product.getDetails().size());
    }

    @Test
    void mapToKeyboard() {
        Product product = productMapper.mapToProduct(keyboardRequest);
        assertNotNull(product);
        assertEquals(keyboard.getCategory(), product.getCategory());
        assertEquals(keyboard.getCurrency(), product.getCurrency());
        assertEquals(keyboard.getAmount(), product.getAmount());
        assertEquals(keyboard.getStock(), product.getStock());
        assertEquals(keyboard.getIssue(), product.getIssue());
        assertEquals(2, product.getDetails().size());
    }

    @Test
    void mapToLaptop() {
        Product product = productMapper.mapToProduct(laptopRequest);
        assertNotNull(product);
        assertEquals(laptop.getCategory(), product.getCategory());
        assertEquals(laptop.getCurrency(), product.getCurrency());
        assertEquals(laptop.getAmount(), product.getAmount());
        assertEquals(laptop.getStock(), product.getStock());
        assertEquals(laptop.getIssue(), product.getIssue());
        assertEquals(5, product.getDetails().size());
    }

    @Test
    void mapToPhone() {
        Product product = productMapper.mapToProduct(phoneRequest);
        assertNotNull(product);
        assertEquals(phone.getCategory(), product.getCategory());
        assertEquals(phone.getCurrency(), product.getCurrency());
        assertEquals(phone.getAmount(), product.getAmount());
        assertEquals(phone.getStock(), product.getStock());
        assertEquals(phone.getIssue(), product.getIssue());
        assertEquals(5, product.getDetails().size());
    }
}