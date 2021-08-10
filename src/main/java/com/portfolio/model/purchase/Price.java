package com.portfolio.model.purchase;

import com.portfolio.enums.Currency;
import lombok.Getter;

import java.util.Objects;

@Getter
public class Price {

    private Currency currency;
    private double amount;

    public Price(Currency currency, double amount) {
        this.currency = currency;
        this.amount = amount;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Price)) return false;
        Price price = (Price) o;
        return Double.compare(price.amount, amount) == 0 &&
                currency == price.currency;
    }

    @Override
    public int hashCode() {
        return Objects.hash(currency, amount);
    }
}

