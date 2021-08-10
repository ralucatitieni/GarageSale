package com.portfolio.validator;

import com.portfolio.entity.order.Order;
import com.portfolio.entity.product.Product;
import com.portfolio.exception.InvalidEmailException;
import com.portfolio.exception.InvalidOrderRequestException;
import com.portfolio.exception.ItemNotFoundException;
import com.portfolio.exception.OrderAlreadyPaidException;
import com.portfolio.repository.OrderRepository;
import com.portfolio.repository.StockRepository;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class OrderRequestValidator {

    private StockRepository stockRepository;
    private EmailValidator emailValidator;
    private OrderRepository orderRepository;

    @Autowired
    public OrderRequestValidator(StockRepository stockRepository, EmailValidator emailValidator, OrderRepository orderRepository) {
        this.stockRepository = stockRepository;
        this.emailValidator = emailValidator;
        this.orderRepository = orderRepository;
    }

    public void validateProductsFromOrderRequest(Order order) throws InvalidOrderRequestException, InvalidEmailException {
        int noOfOccurrences = 0;
        Set<Product> productList = order.getProducts();
        for (Product product : productList) {
            noOfOccurrences += productList.stream().filter(p -> product.getCategory().equals(p.getCategory())).count();
        }

        if (noOfOccurrences > productList.size()) {
            throw new InvalidOrderRequestException("Can't buy 2 objects from the same category");
        }
        for (Product product : productList) {
            if (stockRepository.findById(product.getId()).orElseThrow().getNumberOfItems() == 0) {
                throw new InvalidOrderRequestException("Product out of stock");
            }
        }
        String email = order.getCustomer().getEmail();
        if (!emailValidator.isValid(email)) {
            throw new InvalidEmailException("Email is not valid");
        }
    }

    public void validateOrderByIdRequest(Integer id) throws ItemNotFoundException, OrderAlreadyPaidException {
        Order order = orderRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("Order with id " + id + " not found"));
        if (order.isPaid()) {
            throw new OrderAlreadyPaidException("Order with id " + id + " is already paid");
        }
    }

}
