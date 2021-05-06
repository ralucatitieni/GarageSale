package com.portfolio.repository;

import com.portfolio.model.DisplayProduct;
import com.portfolio.model.product.Product;
import com.portfolio.model.product.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class DisplayProductRepository {

    public ProductRepository productRepository;
    public StockRepository stockRepository;
    List<DisplayProduct> displayProductList = new ArrayList<>();

    @Autowired
    public DisplayProductRepository(ProductRepository productRepository, StockRepository stockRepository) {
        this.productRepository = productRepository;
        this.stockRepository = stockRepository;
    }

    @PostConstruct
    public void setDisplayProductList() {
        for (Product product : productRepository.getAllProducts()) {
            for (Stock stock : stockRepository.getStockList()) {
                if (product.getId().equalsIgnoreCase(stock.getProductId())) {
                    DisplayProduct displayProduct = new DisplayProduct(product, stock);
                    displayProductList.add(displayProduct);
                }
            }
        }
    }


    public List<DisplayProduct> getAllDisplayProducts() {
        return displayProductList;
    }

}