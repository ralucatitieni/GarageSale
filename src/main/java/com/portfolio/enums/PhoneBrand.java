package com.portfolio.enums;

public enum PhoneBrand {
    SAMSUNG,
    APPLE,
    XIAOMI,
    ONE_PLUS,
    HUAWEI;

    public static PhoneBrand getPhoneBrand(String brand) {
        try {
            return PhoneBrand.valueOf(brand.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
