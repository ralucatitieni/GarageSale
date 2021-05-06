package com.portfolio.model.response.product;

import com.portfolio.model.product.Product;
import com.portfolio.model.product.accessory.Headphone;
import com.portfolio.model.product.accessory.Keyboard;
import com.portfolio.model.product.electronic.Laptop;
import com.portfolio.model.product.electronic.Phone;
import com.portfolio.model.purchase.Price;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ProductResponseFactory {

    public ProductResponse createProductResponse(Product product, int stock) {
        ProductResponse productResponse = ProductResponse.builder()
                .productId(product.getId())
                .productCategory(String.valueOf(product.getProductCategory()))
                .price(new Price(product.getPrice().getCurrency(), product.getPrice().getAmount()))
                .issue(product.getIssue())
                .characteristics(createCharacteristics(product))
                .stock(stock).build();
        return productResponse;
    }

    private Map<String, String> createCharacteristics(Product product) {
        Map<String, String> map = new HashMap<>();
        if (product instanceof Laptop) {
            map = createLaptopCharacteristics(product);
        }
        if (product instanceof Phone) {
            map = createPhoneCharacteristics(product);
        }
        if (product instanceof Keyboard) {
            map = createKeyboardCharacteristics(product);
        }
        if (product instanceof Headphone) {
            map = createHeadphoneCharacteristics(product);
        }
        return map;

    }

    private Map<String, String> createLaptopCharacteristics(Product product) {
        Map<String, String> map = new HashMap<>();
        Laptop laptop = (Laptop) product;
        map.put("brand", laptop.getLaptopBrand());
        map.put("model", laptop.getLaptopModel());
        map.put("diagonalDisplay", String.valueOf(laptop.getDiagonalDisplay()));
        map.put("memorySize", String.valueOf(laptop.getMemorySize()));
        map.put("operatingSystem", String.valueOf(laptop.getOperatingSystem()));
        return map;
    }

    private Map<String, String> createPhoneCharacteristics(Product product) {
        Map<String, String> map = new HashMap<>();
        Phone phone = (Phone) product;
        map.put("brand", String.valueOf(phone.getPhoneBrand()));
        map.put("model", phone.getPhoneModel());
        map.put("memorySize", String.valueOf(phone.getMemorySize()));
        map.put("operatingSystem", String.valueOf(phone.getOperatingSystem()));
        map.put("dualSim", String.valueOf(phone.isDualSim()));
        return map;
    }

    private Map<String, String> createKeyboardCharacteristics(Product product) {
        Map<String, String> map = new HashMap<>();
        Keyboard keyboard = (Keyboard) product;
        map.put("brand", keyboard.getKeyboardBrand());
        map.put("type", keyboard.getKeyboardType());
        return map;
    }

    private Map<String, String> createHeadphoneCharacteristics(Product product) {
        Map<String, String> map = new HashMap<>();
        Headphone headphone = (Headphone) product;
        map.put("brand", headphone.getHeadphoneBrand());
        map.put("type", headphone.getHeadphoneType());
        return map;
    }
}