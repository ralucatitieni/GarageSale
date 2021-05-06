package com.portfolio.validator;

import com.portfolio.exception.InvalidEmailException;
import com.portfolio.exception.InvalidOrderRequestException;
import com.portfolio.exception.ItemNotFoundException;
import com.portfolio.model.product.Product;
import com.portfolio.model.purchase.Order;
import com.portfolio.repository.OrderRepository;
import com.portfolio.repository.StockRepository;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

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
        List<Product> productList = order.getItemsInCart();
        for (Product product : productList) {
            noOfOccurrences += productList.stream().filter(p -> product.getProductCategory().equals(p.getProductCategory())).count();
        }

        if (noOfOccurrences > productList.size()) {
            throw new InvalidOrderRequestException("Can't buy 2 objects from the same category");
        }
        for (Product product : productList) {
            if (stockRepository.getStockById(product.getId()).getNumberOfItems() == 0) {
                throw new InvalidOrderRequestException("Product out of stock");
            }
        }
        String email = order.getCustomerDetails().getEmail();
        if (!emailValidator.isValid(email)) {
            throw new InvalidEmailException("Email is not valid");
        }
    }

    public void validateOrderByIdRequest(String id) throws ItemNotFoundException {
        Order order = orderRepository.getOrderById(id);
        if (order == null) {
            throw new ItemNotFoundException("Order with id: " + id + " not found");
        }
        if (order.isPaid()) {
            throw new ItemNotFoundException("Order with id: " + id + " is already paid");
        }
    }
}