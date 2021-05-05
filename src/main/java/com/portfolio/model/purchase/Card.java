package com.portfolio.model.purchase;

import lombok.Getter;

import java.io.Serializable;
import java.time.YearMonth;

@Getter
public class Card implements Serializable {

    private final String cardNumber;
    private final String cardCvv;
    private final YearMonth expire;

    public Card(String cardNumber, String cardCvv, YearMonth expire) {
        this.cardNumber = cardNumber;
        this.cardCvv = cardCvv;
        this.expire = expire;
    }
}
