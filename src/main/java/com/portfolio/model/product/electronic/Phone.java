package com.portfolio.model.product.electronic;

import com.portfolio.enums.MemorySize;
import com.portfolio.enums.PhoneBrand;
import com.portfolio.enums.PhoneOperatingSystem;
import com.portfolio.model.product.Product;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.Objects;

@Getter
@SuperBuilder
public class Phone extends Product {
    private PhoneBrand phoneBrand;
    private String phoneModel;
    private PhoneOperatingSystem operatingSystem;
    private MemorySize memorySize;
    private boolean dualSim;

    @Override
    public String toString() {
        return "PHONE " + '\n' +
                " Brand: " + phoneBrand +
                ", Model: " + phoneModel +
                ", Operating System: " + operatingSystem +
                ", Memory Size: " + memorySize +
                ", Dual Sim: " + dualSim +
                ", Price: " + getPrice().getAmount() + " " + getPrice().getCurrency() +
                ", Issue: " + getIssue() + '\n' +
                " Product ID: " + getId();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Phone phone = (Phone) o;
        return dualSim == phone.dualSim && phoneBrand == phone.phoneBrand && Objects.equals(phoneModel, phone.phoneModel) && operatingSystem == phone.operatingSystem && memorySize == phone.memorySize;
    }

    @Override
    public int hashCode() {
        return Objects.hash(phoneBrand, phoneModel, operatingSystem, memorySize, dualSim);
    }
}
