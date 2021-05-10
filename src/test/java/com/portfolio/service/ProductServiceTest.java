package com.portfolio.service;

import com.portfolio.entity.product.Product;
import com.portfolio.entity.product.Stock;
import com.portfolio.enums.RequestType;
import com.portfolio.exception.InvalidProductRequestException;
import com.portfolio.exception.InvalidRequestException;
import com.portfolio.exception.ItemNotFoundException;
import com.portfolio.model.request.product.ProductMapper;
import com.portfolio.model.request.product.ProductRequest;
import com.portfolio.model.request.product.StockRequest;
import com.portfolio.model.response.Response;
import com.portfolio.model.response.ResponseFactoryProvider;
import com.portfolio.model.response.product.ProductResponse;
import com.portfolio.model.response.product.ProductResponseFactory;
import com.portfolio.repository.ProductRepository;
import com.portfolio.repository.StockRepository;
import com.portfolio.validator.ProductRequestValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class ProductServiceTest {

    // TODO: 5/10/2021 create just one mock: when (getSomeValue).thenReturn(value1, value2, value3)
    @Mock
    private ProductResponse productResponse1;
    @Mock
    private ProductResponse productResponse2;
    @Mock
    private Product product1;
    @Mock
    private Product product2;
    @Mock
    private ProductRequest productRequest1;
    @Mock
    private ProductRequest productRequest2;
    @Mock
    private StockRequest stockRequest;
    @Mock
    private Stock stock;
    private List<Product> productList;
    @Mock
    private ProductMapper productMapper;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private ProductRequestValidator productRequestValidator;
    @Mock
    private ProductResponseFactory productResponseFactory;
    @Mock
    private StockRepository stockRepository;
    @Mock
    private ResponseFactoryProvider responseFactoryProvider;
    private ProductService productService;

    @BeforeEach
    void setUp() {
        when(responseFactoryProvider.getFactory(RequestType.PRODUCT)).thenReturn(productResponseFactory);
        productService = new ProductServiceImpl(productMapper, productRepository, productRequestValidator, stockRepository, responseFactoryProvider);

        productList = new ArrayList<>();
        productList.add(product1);

        when(productResponseFactory.createResponse(product1)).thenReturn(productResponse1);
        when(productRepository.findAll()).thenReturn(productList);
        when(productRepository.save(product2)).thenReturn(product2);
        when(productMapper.mapToProduct(productRequest2)).thenReturn(product2);
        when(productResponseFactory.createResponse(product2)).thenReturn(productResponse2);
        when(productRepository.findById(1)).thenReturn(Optional.of(product1));
    }

    @Test
    void addProductThrowsItemNotFoundException() throws InvalidProductRequestException, ItemNotFoundException {
        doThrow(ItemNotFoundException.class).when(productRequestValidator).validateProductRequest(productRequest1);
        assertThrows(InvalidRequestException.class, () -> productService.addProduct(productRequest1));
        verify(productRequestValidator, atLeastOnce()).validateProductRequest(productRequest1);
    }

    @Test
    void addProductThrowsInvalidProductRequestException() throws InvalidProductRequestException, ItemNotFoundException {
        doThrow(InvalidProductRequestException.class).when(productRequestValidator).validateProductRequest(productRequest1);
        assertThrows(InvalidRequestException.class, () -> productService.addProduct(productRequest1));
        verify(productRequestValidator, atLeastOnce()).validateProductRequest(productRequest1);
    }

    @Test
    void addProduct() throws InvalidProductRequestException, ItemNotFoundException {
        productList.add(product2);

        Response response = productService.addProduct(productRequest2);
        assertNotNull(response);
        assertEquals(productResponse2, response);
        assertEquals(2, productRepository.findAll().size());

        verify(productMapper, atLeastOnce()).mapToProduct(productRequest2);
        verify(productResponseFactory, atLeastOnce()).createResponse(product2);
        verify(productRepository, atLeastOnce()).save(product2);
        verify(productRequestValidator, atLeastOnce()).validateProductRequest(productRequest2);
    }

    @Test
    void getProductThrowException() {
        when(productRepository.findById(0)).thenReturn(Optional.empty());
        assertThrows(InvalidRequestException.class, () -> productService.getProduct(0));
        verify(productRepository, atLeastOnce()).findById(0);
    }

    @Test
    void getProduct() {
        ProductResponse response = productService.getProduct(1);
        assertNotNull(response);
        assertEquals(productResponse1, response);
        verify(productRepository, atLeastOnce()).findById(1);
        verify(productResponseFactory, atLeastOnce()).createResponse(productRepository.findById(1).get());
    }

    @Test
    void getAllProductsFromRepository() {
        List<ProductResponse> productResponses = productService.getAllProducts();
        assertNotNull(productResponses);
        assertEquals(1, productResponses.size());
        verify(productRepository, atLeastOnce()).findAll();
        for (Product product : productList) {
            verify(productResponseFactory, atLeastOnce()).createResponse(product);
        }
    }

    @Test
    void updateStockThrowsException() {
        when(stockRepository.findByProductId(0)).thenReturn(Optional.empty());
        assertThrows(InvalidRequestException.class, () -> productService.updateStock(0, stockRequest));
        verify(stockRepository, atLeastOnce()).findByProductId(0);
    }

    @Test
    void updateStock() {
        when(product1.getStock()).thenReturn(stock);
        when(stockRepository.findByProductId(1)).thenReturn(Optional.of(stock));
        when(stockRepository.save(stock)).thenReturn(stock);

        ProductResponse response = productService.updateStock(1, stockRequest);
        assertNotNull(response);
        verify(stockRepository, atLeastOnce()).findByProductId(1);
        verify(productRepository, atLeastOnce()).save(product1);
        verify(productResponseFactory).createResponse(product1);
    }
}

