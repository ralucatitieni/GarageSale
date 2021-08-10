package com.portfolio;

import com.portfolio.entity.order.Customer;
import com.portfolio.entity.order.Order;
import com.portfolio.entity.product.Product;

import java.util.HashSet;
import java.util.Set;

// TODO: 5/10/2021 remove this class after creating mocks
public class OrderTemplate {
    private static final double CURRENCY_CONVERTER_EUR = 5;
    private static final double CURRENCY_CONVERTER_USD = 4.5;
    private static final String FIRST_NAME = "ANA";
    private static final String LAST_NAME = "IONESCU";
    private static final String EMAIL = "ana.ionescu@gmail.com";


    public static Order createOrder() {
        Product product = ProductTemplate.createHeadphone();
        switch (product.getCurrency()) {
            case "USD":
                product.setAmount(product.getAmount() * CURRENCY_CONVERTER_USD);
                break;
            case "EUR":
                product.setAmount(product.getAmount() * CURRENCY_CONVERTER_EUR);
                break;
        }

        Set<Product> itemsInCart = new HashSet<>();
        itemsInCart.add(product);
        Customer customer = new Customer();
        customer.setId(1);
        customer.setFirstName(FIRST_NAME);
        customer.setLastName(LAST_NAME);
        customer.setEmail(EMAIL);

        Order order = new Order.Builder()
                .total(product.getAmount())
                .products(itemsInCart)
                .customer(customer)
                .build();
        order.setId(1);
        return order;
    }
}
