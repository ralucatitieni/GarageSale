package com.portfolio;

import com.portfolio.model.product.Product;
import com.portfolio.repository.ProductRepository;
import com.portfolio.shop.Shop;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        List<Product> productList = ProductRepository.getAllProducts();
        Shop shop = new Shop(productList);

        shop.showMainMenu();

    }
}
