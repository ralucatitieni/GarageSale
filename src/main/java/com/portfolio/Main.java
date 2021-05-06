package com.portfolio;

import com.portfolio.shop.Shop;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {

        ApplicationContext applicationContext = SpringApplication.run(Main.class, args);
        Shop shop = applicationContext.getBean("shop", Shop.class);
        shop.start();

    }
}
