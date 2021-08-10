package com.portfolio.validator;

import com.portfolio.entity.order.Customer;
import com.portfolio.entity.order.Order;
import com.portfolio.entity.product.Product;
import com.portfolio.entity.product.Stock;
import com.portfolio.exception.InvalidEmailException;
import com.portfolio.exception.InvalidOrderRequestException;
import com.portfolio.exception.ItemNotFoundException;
import com.portfolio.exception.OrderAlreadyPaidException;
import com.portfolio.repository.OrderRepository;
import com.portfolio.repository.StockRepository;
import org.apache.commons.validator.routines.EmailValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class OrderRequestValidatorTest {

    @Mock
    Customer customer;
    @Mock
    private Order order;
    @Mock
    private Stock stock;
    @Mock
    private Product product1;
    @Mock
    private Product product2;
    @Mock
    private StockRepository stockRepository;
    @Mock
    private EmailValidator emailValidator;
    @Mock
    private OrderRepository orderRepository;
    private OrderRequestValidator orderRequestValidator;

    @BeforeEach
    void setup() {
        orderRequestValidator = new OrderRequestValidator(stockRepository, emailValidator, orderRepository);

        when(stock.getNumberOfItems()).thenReturn(5);
        when(stock.getId()).thenReturn(1);
        when(order.getCustomer()).thenReturn(customer);
        when(customer.getEmail()).thenReturn("ana.ionescu@gmail.com");
        when(stock.getId()).thenReturn(1);
        when(stockRepository.findById(1)).thenReturn(Optional.of(stock));
        when(orderRepository.findById(1)).thenReturn(Optional.of(order));
    }


    // @Test
    void validateEmptyStock() {
        // TODO: 5/10/2021
        //  when(stock.getNumberOfItems()).thenReturn(0);
        //  Exception exception = assertThrows(InvalidOrderRequestException.class, () -> orderRequestValidator.validateProductsFromOrderRequest(order));
        //  assertEquals("Product out of stock", exception.getMessage());
    }

    @Test
    void validateSameCategory() {
        when(stock.getNumberOfItems()).thenReturn(5);
        when(stock.getId()).thenReturn(1);

        when(product1.getCategory()).thenReturn("HEADPHONE");
        when(product1.getIssue()).thenReturn("perfectly working");
        when(product2.getCategory()).thenReturn("HEADPHONE");
        when(product1.getIssue()).thenReturn("very scratched");
        Set<Product> products = new HashSet<>(Arrays.asList(product1, product2));
        when(order.getProducts()).thenReturn(products);
        Exception exception = assertThrows(InvalidOrderRequestException.class, () -> orderRequestValidator.validateProductsFromOrderRequest(order));
        assertEquals("Can't buy 2 objects from the same category", exception.getMessage());
    }

    @Test
    void validateEmail() {
        when(customer.getEmail()).thenReturn("someWrongEmail@");
        Exception exception = assertThrows(InvalidEmailException.class, () -> orderRequestValidator.validateProductsFromOrderRequest(order));
        assertEquals("Email is not valid", exception.getMessage());
    }

    @Test
    void validateByIdRequested() {
        when(order.isPaid()).thenReturn(true);
        Exception exception = assertThrows(OrderAlreadyPaidException.class, () -> orderRequestValidator.validateOrderByIdRequest(1));
        assertEquals("Order with id " + 1 + " is already paid", exception.getMessage());
    }

    @Test
    void validateByIdRequestedIfOrderDoesNotExist() {
        when(orderRepository.findById(1)).thenReturn(Optional.empty());
        Exception exception = assertThrows(ItemNotFoundException.class, () -> orderRequestValidator.validateOrderByIdRequest(1));
        assertEquals("Order with id " + 1 + " not found", exception.getMessage());
    }

}
