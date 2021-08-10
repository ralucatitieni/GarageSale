package com.portfolio.model.request.order;

import com.portfolio.entity.order.Customer;
import com.portfolio.entity.order.Order;
import com.portfolio.entity.product.Product;
import com.portfolio.enums.Currency;
import com.portfolio.repository.CustomerRepository;
import com.portfolio.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class OrderMapper {

    private static final double CURRENCY_CONVERTER_EUR = 5;
    private static final double CURRENCY_CONVERTER_USD = 4.5;
    private ProductRepository productRepository;
    private CustomerRepository customerRepository;

    @Autowired
    public OrderMapper(ProductRepository productRepository, CustomerRepository customerRepository) {
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
    }

    public Order mapToOrder(OrderRequest orderRequest) {

        Customer customer = createCustomer(orderRequest.getCustomerDetailsRequest());
        Set<Product> productsInCart = createProductListInCart(orderRequest.getProductIds());
        Order order = new Order.Builder()
                .total(getTotalAmountInRON(productsInCart))
                .products(productsInCart)
                .customer(customer)
                .build();
        return order;
    }

    private Set<Product> createProductListInCart(Set<Integer> ids) {
        Set<Product> itemsInCart = new HashSet<>();
        itemsInCart.addAll(productRepository.findAllById(ids));
        return itemsInCart;
    }

    private Customer createCustomer(CustomerDetailsRequest customerDetailsRequest) {
        Customer customer = customerRepository.findByEmail(customerDetailsRequest.getEmail())
                .orElse(new Customer(customerDetailsRequest.getFirstName().trim(), customerDetailsRequest.getLastName().trim(), customerDetailsRequest.getEmail().trim()));

        return customer;
    }

    private double getTotalAmountInRON(Set<Product> productList) {
        double amountInRON = 0;
        for (Product product : productList) {
            if (product.getCurrency().equals(String.valueOf(Currency.RON))) {
                amountInRON += (product.getAmount());
            } else if (product.getCurrency().equals(String.valueOf(Currency.EUR))) {
                amountInRON += (product.getAmount() * CURRENCY_CONVERTER_EUR);
            } else if (product.getCurrency().equals(String.valueOf(Currency.USD))) {
                amountInRON += (product.getAmount() * CURRENCY_CONVERTER_USD);
            }
        }
        return amountInRON;
    }
}
