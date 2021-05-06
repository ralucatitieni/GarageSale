package com.portfolio.enums;

public enum Currency {
    RON,
    EUR,
    USD;

    public static Currency getCurrency(String currency) {
        try {
            return Currency.valueOf(currency.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
