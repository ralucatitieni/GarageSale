package com.portfolio.model.purchase;

import com.portfolio.model.product.Product;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Purchase {

    private static final double CURRENCY_CONVERTER_EUR = 5;
    private static final double CURRENCY_CONVERTER_USD = 4.5;
    private CustomerDetails customerDetails;
    private Card card;
    private List<Product> itemsInCart;

    public Purchase(List<Product> itemsInCart) {
        this.itemsInCart = itemsInCart;
    }

    public double getTotalAmountInRON() {
        double amountInRON = 0;
        for (Product product : itemsInCart) {
            amountInRON += (product.getPrice().getAmount());
        }
        return amountInRON;
    }

    public double getTotalAmountInEUR() {
        double amountInEUR = 0;
        for (Product product : itemsInCart) {
            amountInEUR += (product.getPrice().getAmount() / CURRENCY_CONVERTER_EUR);
        }
        return amountInEUR;
    }

    public double getTotalAmountInUSD() {
        double amountInUSD = 0;
        for (Product product : itemsInCart) {
            amountInUSD += (product.getPrice().getAmount() / CURRENCY_CONVERTER_USD);
        }
        return amountInUSD;
    }

}
