package com.portfolio.enums;

public enum MemorySize {
    RAM2GB,
    RAM4GB,
    RAM8GB,
    RAM16GB,
    RAM32GB;

    public static MemorySize getMemorySize(Integer memorySize) {
        try {
            return MemorySize.valueOf("RAM" + memorySize + "GB");
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
