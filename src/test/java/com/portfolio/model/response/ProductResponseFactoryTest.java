package com.portfolio.model.response;

import com.portfolio.ProductTemplate;
import com.portfolio.entity.product.Product;
import com.portfolio.enums.Currency;
import com.portfolio.model.purchase.Price;
import com.portfolio.model.response.product.ProductResponse;
import com.portfolio.model.response.product.ProductResponseFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
class ProductResponseFactoryTest {

    private static final String HEADPHONE_CATEGORY = "HEADPHONE";
    private static final String HEADPHONE_BRAND = "JBL";
    private static final String HEADPHONE_TYPE = "IN-EAR";
    private static final String CURRENCY = "USD";
    private static final double AMOUNT = 30.0;
    private static final int STOCK = 5;
    private static final int ID = 1;
    private static final List<String> ISSUES = new ArrayList<>(Arrays.asList("something"));
    Map<String, String> headphoneCharacteristics = new TreeMap<>();
    // TODO: 5/10/2021 mock product
    private Product product;
    private ProductResponseFactory productResponseFactory;

    @BeforeEach
    void setup() {
        productResponseFactory = new ProductResponseFactory();

        product = ProductTemplate.createHeadphone();
        headphoneCharacteristics.put("Brand", HEADPHONE_BRAND);
        headphoneCharacteristics.put("Type", HEADPHONE_TYPE);

    }

    @Test
    void createResponse() {
        ProductResponse response = productResponseFactory.createResponse(product);

        assertNotNull(response);
        assertEquals(HEADPHONE_CATEGORY, response.getCategory());
        assertEquals(new Price(Currency.valueOf(CURRENCY), AMOUNT), response.getPrice());
        assertEquals(STOCK, response.getStock());
        assertEquals(ISSUES, response.getIssues());
        assertEquals(headphoneCharacteristics, response.getCharacteristics());
        assertEquals(ID, response.getId());
    }
}