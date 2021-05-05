package com.portfolio.model.purchase;

import com.portfolio.enums.Currency;
import lombok.Getter;

@Getter
public class Price {

    private static final double CURRENCY_CONVERTER_EUR = 5;
    private static final double CURRENCY_CONVERTER_USD = 4.5;
    private Currency currency;
    private double amount;

    public Price(Currency currency, double amount) {
        this.currency = currency;
        switch (currency) {
            case EUR:
                this.amount = amount * CURRENCY_CONVERTER_EUR;
                break;
            case USD:
                this.amount = amount * CURRENCY_CONVERTER_USD;
                break;
            default:
                this.amount = amount;
                break;
        }
    }
}
