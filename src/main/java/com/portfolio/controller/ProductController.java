package com.portfolio.controller;


import com.portfolio.model.request.product.ProductRequest;
import com.portfolio.model.request.product.StockRequest;
import com.portfolio.model.response.product.ProductResponse;
import com.portfolio.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
public class ProductController {

    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductResponse>> getProducts() {
        return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
    }

    @GetMapping("/products/")
    public ResponseEntity<ProductResponse> getProduct(@RequestParam Integer id) {
        return new ResponseEntity<>(productService.getProduct(id), HttpStatus.OK);
    }

    @PostMapping("/product")
    public ResponseEntity<ProductResponse> addProduct(@Valid @RequestBody ProductRequest productRequest) {
        ProductResponse productResponse = productService.addProduct(productRequest);
        return new ResponseEntity<>(productResponse, HttpStatus.CREATED);
    }

    @PostMapping("/products/stock")
    public ResponseEntity<ProductResponse> updateStock(@RequestParam Integer id, @Valid @RequestBody StockRequest stockRequest) {
        return new ResponseEntity<>(productService.updateStock(id, stockRequest), HttpStatus.OK);
    }
}
