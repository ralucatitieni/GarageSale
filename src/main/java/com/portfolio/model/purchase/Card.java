package com.portfolio.model.purchase;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.YearMonth;

@Getter
@Setter
@AllArgsConstructor
public class Card implements Serializable {

    private String cardNumber;
    private String cardCvv;
    private YearMonth expire;

    @Override
    public String toString() {
        return "CARD: XXXX " + cardNumber.substring(12);
    }
}
