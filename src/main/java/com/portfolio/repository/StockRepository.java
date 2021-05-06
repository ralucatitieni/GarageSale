package com.portfolio.repository;

import com.portfolio.model.product.Product;
import com.portfolio.model.product.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class StockRepository {


    private final List<Stock> stockList = new ArrayList<>();
    private final ProductRepository productRepository;

    @Autowired
    public StockRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    @PostConstruct
    private void setStockList() {
        for (Product product : productRepository.getAllProducts()) {
            Stock stock = new Stock(product.getId());
            stockList.add(stock);
        }
    }

    public List<Stock> getStockList() {
        return stockList;
    }

}