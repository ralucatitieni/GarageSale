package com.portfolio.model.request;

import com.portfolio.ProductTemplate;
import com.portfolio.entity.order.Order;
import com.portfolio.entity.product.Product;
import com.portfolio.model.request.order.CustomerDetailsRequest;
import com.portfolio.model.request.order.OrderMapper;
import com.portfolio.model.request.order.OrderRequest;
import com.portfolio.repository.CustomerRepository;
import com.portfolio.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class OrderMapperTest {

    public static final int EXPECTED_TOTAL_AMOUNT = 135;
    private static final int ID = 1;
    private static final String FIRST_NAME = "ANA";
    private static final String LAST_NAME = "IONESCU";
    private static final String EMAIL = "ana.ionescu@gmail.com";
    @Mock
    private OrderRequest orderRequest;
    @Mock
    private CustomerDetailsRequest customerDetailsRequest;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private CustomerRepository customerRepository;
    private OrderMapper orderMapper;


    @BeforeEach
    void setup() {
        orderMapper = new OrderMapper(productRepository, customerRepository);
        // TODO: 5/10/2021 create mock
        Product product = ProductTemplate.createHeadphone();
        List<Product> productList = new ArrayList<>();
        productList.add(product);
        Set<Integer> ids = new HashSet<>(Arrays.asList(1));
        when(orderRequest.getProductIds()).thenReturn(ids);
        when(customerDetailsRequest.getFirstName()).thenReturn(FIRST_NAME);
        when(customerDetailsRequest.getLastName()).thenReturn(LAST_NAME);
        when(customerDetailsRequest.getEmail()).thenReturn(EMAIL);
        when(orderRequest.getCustomerDetailsRequest()).thenReturn(customerDetailsRequest);


        when(productRepository.findById(ID)).thenReturn(Optional.of(product));
        when(customerRepository.findByEmail(orderRequest.getCustomerDetailsRequest().getEmail())).thenReturn(Optional.empty());
        when(productRepository.findAllById(orderRequest.getProductIds())).thenReturn(productList);
    }

    @Test
    void mapToOrder() {
        Order response = orderMapper.mapToOrder(orderRequest);
        assertNotNull(response);
        assertEquals(EXPECTED_TOTAL_AMOUNT, response.getTotal());
        assertEquals(FIRST_NAME, response.getCustomer().getFirstName());
        assertEquals(LAST_NAME, response.getCustomer().getLastName());
        assertEquals(EMAIL, response.getCustomer().getEmail());
        assertEquals(1, response.getProducts().size());
    }
}

