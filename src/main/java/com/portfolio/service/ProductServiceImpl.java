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
import com.portfolio.model.response.ResponseFactory;
import com.portfolio.model.response.ResponseFactoryProvider;
import com.portfolio.model.response.product.ProductResponse;
import com.portfolio.repository.ProductRepository;
import com.portfolio.repository.StockRepository;
import com.portfolio.validator.ProductRequestValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
    private ProductMapper productMapper;
    private ProductRepository productRepository;
    private ProductRequestValidator productRequestValidator;
    private StockRepository stockRepository;
    private ResponseFactory<Product, ProductResponse> productResponseFactory;

    @Autowired
    public ProductServiceImpl(ProductMapper productMapper, ProductRepository productRepository,
                              ProductRequestValidator productRequestValidator,
                              StockRepository stockRepository, ResponseFactoryProvider responseFactoryProvider) {
        this.productMapper = productMapper;
        this.productRepository = productRepository;
        this.productRequestValidator = productRequestValidator;
        this.stockRepository = stockRepository;
        this.productResponseFactory = responseFactoryProvider.getFactory(RequestType.PRODUCT);
    }

    @Override
    public ProductResponse addProduct(ProductRequest productRequest) {
        try {
            productRequestValidator.validateProductRequest(productRequest);
        } catch (ItemNotFoundException e) {
            logger.error(e.getMessage());
            throw new InvalidRequestException(e.getMessage(), e);
        } catch (InvalidProductRequestException e) {
            logger.error(e.getMessage());
            throw new InvalidRequestException(e.getMessage(), e);
        }
        Product newProduct = productMapper.mapToProduct(productRequest);
        productRepository.save(newProduct);
        ProductResponse newProductResponse = productResponseFactory.createResponse(newProduct);
        logger.info("Added product " + newProduct.getId());
        return newProductResponse;
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().stream().map(p -> productResponseFactory.createResponse(p))
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponse getProduct(int id) {
        ProductResponse productResponse;
        try {
            Product product = productRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("Item " + id + " does not exist"));
            productResponse = productResponseFactory.createResponse(product);
            logger.info("Display product: " + id);
        } catch (ItemNotFoundException e) {
            logger.error(e.getMessage());
            throw new InvalidRequestException(e.getMessage(), e);
        }
        return productResponse;
    }

    @Override
    public ProductResponse updateStock(int id, StockRequest stockRequest) {
        ProductResponse productResponse;
        try {
            Stock stock = stockRepository.findByProductId(id).orElseThrow(() -> new ItemNotFoundException("Product with id " + id + " not found"));
            stock.setNumberOfItems(stock.getNumberOfItems() + stockRequest.getStock());
            stockRepository.save(stock);
            Product product = productRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("Product with id " + id + " not found"));
            product.setStock(stock);
            productRepository.save(product);
            productResponse = productResponseFactory.createResponse(product);
            logger.info("Update stock for product: " + id);
        } catch (ItemNotFoundException e) {
            logger.error(e.getMessage());
            throw new InvalidRequestException(e.getMessage(), e);
        }
        return productResponse;
    }
}
