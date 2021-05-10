package com.portfolio.service;

import com.portfolio.entity.order.Order;
import com.portfolio.entity.product.Product;
import com.portfolio.entity.product.Stock;
import com.portfolio.enums.RequestType;
import com.portfolio.exception.InvalidEmailException;
import com.portfolio.exception.InvalidOrderRequestException;
import com.portfolio.exception.InvalidRequestException;
import com.portfolio.exception.ItemNotFoundException;
import com.portfolio.model.request.order.OrderMapper;
import com.portfolio.model.request.order.OrderRequest;
import com.portfolio.model.response.ResponseFactory;
import com.portfolio.model.response.ResponseFactoryProvider;
import com.portfolio.model.response.order.OrderResponse;
import com.portfolio.repository.CustomerRepository;
import com.portfolio.repository.OrderRepository;
import com.portfolio.repository.ProductRepository;
import com.portfolio.repository.StockRepository;
import com.portfolio.validator.OrderRequestValidator;
import com.portfolio.validator.ProductRequestValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class OrderServiceTest {

    @Mock
    private Order order;
    @Mock
    private Product product;
    @Mock
    private OrderRequest orderRequest;
    @Mock
    private OrderResponse orderResponse;
    @Mock
    private Stock stock;
    @Mock
    private OrderMapper orderMapper;
    @Mock
    private OrderRequestValidator orderRequestValidator;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private ProductRequestValidator productRequestValidator;
    @Mock
    private StockRepository stockRepository;
    @Mock
    private ResponseFactory<Order, OrderResponse> orderResponseFactory;
    @Mock
    private ResponseFactoryProvider responseFactoryProvider;
    @Mock
    private ProductRepository productRepository;
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        when(responseFactoryProvider.getFactory(RequestType.ORDER)).thenReturn(orderResponseFactory);
        orderService = new OrderServiceImpl(orderMapper, orderRequestValidator, orderRepository, customerRepository, productRequestValidator, stockRepository, responseFactoryProvider);

        when(product.getCategory()).thenReturn("LAPTOP");

        when(orderRepository.save(order)).thenReturn(order);
        when(orderMapper.mapToOrder(orderRequest)).thenReturn(order);
        when(productRepository.findById(1)).thenReturn(Optional.of(product));
        when(orderResponseFactory.createResponse(order)).thenReturn(orderResponse);

        when(stock.getId()).thenReturn(1);
        when(stock.getNumberOfItems()).thenReturn(3);

        Set<Integer> productIds = new HashSet<>(Arrays.asList(1));
        when(orderRequest.getProductIds()).thenReturn(productIds);

        when(stockRepository.findById(stock.getId())).thenReturn(Optional.of(stock));
        when(stockRepository.save(stock)).thenReturn(stock);
    }

    @Test
    void placeOrderThrowsItemNotFoundRequestException() throws InvalidRequestException, ItemNotFoundException {
        doThrow(ItemNotFoundException.class).when(productRequestValidator).validateProductByIdRequest(1);
        assertThrows(InvalidRequestException.class, () -> orderService.placeOrder(orderRequest));
        verify(productRequestValidator, atLeastOnce()).validateProductByIdRequest(1);
    }

    @Test
    void placeOrderThrowsInvalidEmailException() throws InvalidRequestException, InvalidEmailException, InvalidOrderRequestException {
        doThrow(InvalidEmailException.class).when(orderRequestValidator).validateProductsFromOrderRequest(order);
        assertThrows(InvalidRequestException.class, () -> orderService.placeOrder(orderRequest));
        verify(orderRequestValidator, atLeastOnce()).validateProductsFromOrderRequest(order);
    }

    @Test
    void placeOrderThrowsInvalidProductRequestException() throws InvalidRequestException, InvalidEmailException, InvalidOrderRequestException {
        doThrow(InvalidOrderRequestException.class).when(orderRequestValidator).validateProductsFromOrderRequest(order);
        assertThrows(InvalidRequestException.class, () -> orderService.placeOrder(orderRequest));
        verify(orderRequestValidator, atLeastOnce()).validateProductsFromOrderRequest(order);
    }

    @Test
    void placeOrder() {
        OrderResponse response = orderService.placeOrder(orderRequest);

        assertNotNull(response);
        assertEquals(orderResponse, response);

        verify(orderMapper, atLeastOnce()).mapToOrder(orderRequest);
        verify(orderResponseFactory, atLeastOnce()).createResponse(order);
        verify(orderRepository, atLeastOnce()).save(order);
        verify(stockRepository, atLeastOnce()).save(stock);
        verify(stockRepository, atLeastOnce()).findById(stock.getId());
    }

}
