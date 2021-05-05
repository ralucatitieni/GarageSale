package com.portfolio.model.product.electronic;

import com.portfolio.enums.MemorySize;
import com.portfolio.enums.PhoneBrand;
import com.portfolio.enums.PhoneOperatingSystem;
import com.portfolio.enums.ProductCategory;
import com.portfolio.model.product.Product;
import com.portfolio.model.purchase.Price;
import lombok.Getter;

@Getter
public class Phone extends Product {
    private final PhoneBrand phoneBrand;
    private final String phoneModel;
    private final PhoneOperatingSystem operatingSystem;
    private final MemorySize memorySize;
    private final boolean dualSim;

    public Phone(Price price, String issue, ProductCategory productCategory, PhoneOperatingSystem operatingSystem, MemorySize memorySize,
                 PhoneBrand phoneBrand, String phoneModel, boolean dualSim) {

        super(price, issue, productCategory);
        this.phoneBrand = phoneBrand;
        this.phoneModel = phoneModel;
        this.operatingSystem = operatingSystem;
        this.memorySize = memorySize;
        this.dualSim = dualSim;
    }

    @Override
    public String toString() {
        return "PHONE " + '\n' +
                " Brand: " + phoneBrand +
                ", Model: " + phoneModel + '\'' +
                ", Operating System: " + operatingSystem +
                ", Memory Size: " + memorySize +
                ", Dual Sim: " + dualSim +
                ", Price: " + getPrice().getAmount() + "RON" +
                ", Issue: " + getIssue() + '\n' +
                " Product ID " + getId();
    }

}