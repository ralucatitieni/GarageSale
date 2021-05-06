package com.portfolio.repository;

import com.portfolio.model.purchase.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderRepository {

    private List<Order> orderList = new ArrayList<>();

    public List<Order> getAllOrders() {
        return orderList;
    }

    public void registerOrder(Order order) {
        orderList.add(order);
    }
}
