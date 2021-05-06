package com.portfolio.service;

import com.portfolio.enums.Currency;
import com.portfolio.model.product.Product;
import com.portfolio.model.product.Stock;
import com.portfolio.model.purchase.Order;
import com.portfolio.repository.ProductRepository;
import com.portfolio.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderService {

    private static final double CONVERSION_RATE_EUR = 5;
    private static final double CONVERSION_RATE_USD = 4.5;
    private ProductRepository productRepository;
    private StockRepository stockRepository;


    @Autowired
    public OrderService(ProductRepository productRepository, StockRepository stockRepository) {
        this.productRepository = productRepository;
        this.stockRepository = stockRepository;
    }

    public Product returnProductFromProductRepo(String id) {
        return productRepository.getProductById(id);
    }

    public Product returnProductFromOrder(String id, Order order) {
        return order.getItemsInCart().stream().
                filter(p -> p.getId().equalsIgnoreCase(id)).
                findFirst().orElse(null);
    }

    public List<Product> addToCart(String id, Order order) {
        Product product = returnProductFromProductRepo(id);
        if (product != null) {
            order.getItemsInCart().add(product);
            for (Stock stock : stockRepository.getStockList()) {
                if (stock.getProductId().equalsIgnoreCase(product.getId())) {
                    stock.setNumberOfItems(stock.getNumberOfItems() - 1);
                }
            }
        }
        return order.getItemsInCart();
    }

    public List<Product> removeItemFromCart(String id, Order order) {
        for (Stock stock : stockRepository.getStockList()) {
            if (stock.getProductId().equalsIgnoreCase(id)) {
                stock.setNumberOfItems(stock.getNumberOfItems() + 1);
            }
        }
        order.getItemsInCart().removeIf(p -> p.getId().equalsIgnoreCase(id));
        return order.getItemsInCart();
    }

    public double getTotalAmountInRON(Order order) {
        double amountInRON = 0;
        for (Product product : order.getItemsInCart()) {
            if (product.getPrice().getCurrency().equals(Currency.RON)) {
                amountInRON += (product.getPrice().getAmount());
            } else if (product.getPrice().getCurrency().equals(Currency.EUR)) {
                amountInRON += (product.getPrice().getAmount() * CONVERSION_RATE_EUR);
            } else if (product.getPrice().getCurrency().equals(Currency.USD)) {
                amountInRON += (product.getPrice().getAmount() * CONVERSION_RATE_USD);
            }
        }
        return amountInRON;
    }

    public double getTotalAmountInEUR(Order order) {
        return getTotalAmountInRON(order) / CONVERSION_RATE_EUR;
    }

    public double getTotalAmountInUSD(Order order) {
        return getTotalAmountInRON(order) / CONVERSION_RATE_USD;
    }

}
