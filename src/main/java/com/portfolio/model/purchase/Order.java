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
    private boolean payed;
    private CustomerDetails customerDetails;
    private Card card;

    public Order() {
        this.itemsInCart = new ArrayList<>();
        this.id = UUID.randomUUID().toString().substring(0, 8);
        this.payed = false;
    }

    public boolean isPayed() {
        return payed;
    }

    @Override
    public String toString() {
        return "ORDER " + '\n' +
                "ID='" + id + '\'' +
                ", Purchased items: " + itemsInCart +
                ", PAYED" + payed + '\n' +
                ", Customer Details: " + customerDetails +
                ", " + card;
    }
}
