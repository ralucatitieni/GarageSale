package com.portfolio.enums;

public enum PhoneOperatingSystem {
    ANDROID,
    IOS;

    public static PhoneOperatingSystem getPhoneOperatingSystem(String operatingSystem) {
        try {
            return PhoneOperatingSystem.valueOf(operatingSystem.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
