package com.portfolio.service;

import com.portfolio.exception.InvalidProductRequestException;
import com.portfolio.exception.InvalidRequestException;
import com.portfolio.exception.ItemNotFoundException;
import com.portfolio.model.product.Product;
import com.portfolio.model.product.Stock;
import com.portfolio.model.request.product.ProductMapper;
import com.portfolio.model.request.product.ProductRequest;
import com.portfolio.model.response.product.ProductResponse;
import com.portfolio.model.response.product.ProductResponseFactory;
import com.portfolio.repository.ProductRepository;
import com.portfolio.repository.StockRepository;
import com.portfolio.validator.ProductRequestValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductServiceImpl implements ProductService {

    private ProductMapper productMapper;
    private ProductRepository productRepository;
    private StockRepository stockRepository;
    private ProductRequestValidator productRequestValidator;
    private ProductResponseFactory productResponseFactory;

    @Autowired
    public ProductServiceImpl(ProductMapper productMapper, ProductRepository productRepository,
                              StockRepository stockRepository, ProductRequestValidator productRequestValidator,
                              ProductResponseFactory productResponseFactory) {
        this.productMapper = productMapper;
        this.productRepository = productRepository;
        this.stockRepository = stockRepository;
        this.productRequestValidator = productRequestValidator;
        this.productResponseFactory = productResponseFactory;
    }

    @Override
    public ProductResponse addProduct(ProductRequest productRequest) {
        try {
            productRequestValidator.validateProductRequest(productRequest);
        } catch (ItemNotFoundException e) {
            throw new InvalidRequestException(e.getMessage(), e);
        } catch (InvalidProductRequestException e) {
            throw new InvalidRequestException(e.getMessage(), e);
        }
        Product newProduct = productMapper.mapProduct(productRequest);
        ProductResponse newProductResponse;
        String productId;

        if (productRepository.getProductId(newProduct) != null) {
            productId = productRepository.getProductId(newProduct);
            int itemsInStock = stockRepository.getStockById(productId).getNumberOfItems();
            Stock stock = stockRepository.getStockById(productId);
            stock.setNumberOfItems(itemsInStock + productRequest.getStock());
            newProductResponse = productResponseFactory.createProductResponse(newProduct, stock.getNumberOfItems());
        } else {
            Stock newStock = new Stock(newProduct.getId());
            newStock.setNumberOfItems(productRequest.getStock());
            stockRepository.getStockList().add(newStock);
            productRepository.getAllProducts().add(newProduct);
            newProductResponse = productResponseFactory.createProductResponse(newProduct, productRequest.getStock());
        }
        return newProductResponse;
    }

    @Override
    public List<ProductResponse> getProductResponses() {
        return productRepository.getAllProducts().stream().map(p -> productResponseFactory.createProductResponse(p, stockRepository.getStockById(p.getId()).getNumberOfItems()))
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponse getProductResponseById(String id) {
        Product product = productRepository.getProductById(id);
        ProductResponse productResponse;
        try {
            productRequestValidator.validateProductByIdRequest(id);
            Stock stock = stockRepository.getStockById(id);
            productResponse = productResponseFactory.createProductResponse(product, stock.getNumberOfItems());
        } catch (ItemNotFoundException e) {
            throw new InvalidRequestException(e.getMessage(), e);
        }
        return productResponse;
    }
}