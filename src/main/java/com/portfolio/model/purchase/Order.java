package com.portfolio.model.purchase;

import com.portfolio.model.product.Product;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class Order {

    private String id;
    private List<Product> itemsInCart;
    private boolean paid;
    private CustomerDetails customerDetails;
    private Card card;

    public Order() {
        this.itemsInCart = new ArrayList<>();
        this.id = UUID.randomUUID().toString().substring(0, 8);
        this.paid = false;
    }

    public boolean isPaid() {
        return paid;
    }

    @Override
    public String toString() {
        return "ORDER " + '\n' +
                "ID='" + id + '\'' +
                ", Purchased items: " + itemsInCart +
                ", PAYED" + paid + '\n' +
                ", Customer Details: " + customerDetails +
                ", " + card;
    }
}
