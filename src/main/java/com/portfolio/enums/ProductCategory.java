package com.portfolio.enums;

public enum ProductCategory {
    LAPTOP,
    PHONE,
    TABLET,
    TV,
    HEADPHONE,
    KEYBOARD;

    public static ProductCategory getProductCategory(String category) {
        try {
            return ProductCategory.valueOf(category.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
