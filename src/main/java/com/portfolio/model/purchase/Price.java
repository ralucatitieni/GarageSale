package com.portfolio.model.purchase;

import com.portfolio.enums.Currency;
import lombok.Getter;

@Getter
public class Price {

    private Currency currency;
    private double amount;

    public Price(Currency currency, double amount) {
        this.currency = currency;
        this.amount = amount;

    }
}

