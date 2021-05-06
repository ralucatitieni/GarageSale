package com.portfolio.enums;

public enum LaptopOperatingSystem {
    MAC_OS,
    LINUX,
    WINDOWS;

    public static LaptopOperatingSystem getLaptopOperatingSystem(String operatingSystem) {
        try {
            return LaptopOperatingSystem.valueOf(operatingSystem.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
