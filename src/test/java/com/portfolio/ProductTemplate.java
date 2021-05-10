package com.portfolio;

import com.portfolio.entity.product.Product;
import com.portfolio.entity.product.ProductDetail;
import com.portfolio.entity.product.Stock;
import com.portfolio.model.request.product.*;

import java.util.*;
// TODO: 5/10/2021 remove this class after creating mocks

public class
ProductTemplate {

    public static final String LAPTOP_MODEL = "Asus 2021";
    public static final String LAPTOP_OPERATING_SYSTEM = "WINDOWS";
    public static final int DIAGONAL_DISPLAY = 21;
    public static final int MEMORY_SIZE = 16;
    public static final String PHONE_BRAND = "SAMSUNG";
    public static final String PHONE_MODEL = "Samsung 2021";
    public static final String PHONE_OPERATING_SYSTEM = "ANDROID";
    private static final String HEADPHONE_CATEGORY = "HEADPHONE";
    private static final String HEADPHONE_BRAND = "JBL";
    private static final String HEADPHONE_TYPE = "IN-EAR";
    private static final String LAPTOP_CATEGORY = "LAPTOP";
    private static final String LAPTOP_BRAND = "ASUS";
    private static final String PHONE_CATEGORY = "PHONE";
    private static final String KEYBOARD_CATEGORY = "KEYBOARD";
    private static final String KEYBOARD_BRAND = "LOGITECH";
    private static final String TYPE_KEYBOARD = "WIRELESS";

    private static final String CURRENCY = "USD";
    private static final double AMOUNT = 30.0;
    private static final int STOCK = 5;
    private static final int ID_1 = 1;
    private static final int ID_2 = 2;
    private static final List<ProductDetail> HEADPHONE_DETAILS = new ArrayList<>();
    private static final List<ProductDetail> KEYBOARD_DETAILS = new ArrayList<>();
    private static final List<ProductDetail> LAPTOP_DETAILS = new ArrayList<>();
    private static final List<ProductDetail> PHONE_DETAILS = new ArrayList<>();
    private static final List<String> ISSUES = new ArrayList<>(Arrays.asList("something"));

    public static ProductRequest createLaptopRequest() {
        List<String> ISSUES = new ArrayList<>();
        ISSUES.add("something");
        ProductRequest laptopRequest = new ProductRequest();
        laptopRequest.setCategory(LAPTOP_CATEGORY);
        laptopRequest.setCurrency(CURRENCY);
        laptopRequest.setAmount(AMOUNT);
        laptopRequest.setStock(STOCK);
        laptopRequest.setIssues(ISSUES);
        laptopRequest.setLaptopRequest(new LaptopRequest(LAPTOP_BRAND, LAPTOP_MODEL, MEMORY_SIZE, LAPTOP_OPERATING_SYSTEM, DIAGONAL_DISPLAY));
        return laptopRequest;
    }

    public static ProductRequest createPhoneRequest() {


        ProductRequest phoneRequest = new ProductRequest();
        phoneRequest.setCategory(PHONE_CATEGORY);
        phoneRequest.setCurrency(CURRENCY);
        phoneRequest.setAmount(AMOUNT);
        phoneRequest.setStock(STOCK);
        phoneRequest.setIssues(ISSUES);
        phoneRequest.setPhoneRequest(new PhoneRequest(PHONE_BRAND, PHONE_MODEL, PHONE_OPERATING_SYSTEM, 8, true));
        return phoneRequest;
    }


    public static ProductRequest createHeadphoneRequest() {

        ProductRequest headphoneRequest = new ProductRequest();
        headphoneRequest.setCategory(HEADPHONE_CATEGORY);
        headphoneRequest.setCurrency(CURRENCY);
        headphoneRequest.setAmount(AMOUNT);
        headphoneRequest.setStock(STOCK);
        headphoneRequest.setIssues(ISSUES);
        headphoneRequest.setHeadphoneRequest(new HeadphoneRequest(HEADPHONE_BRAND, HEADPHONE_TYPE));
        return headphoneRequest;
    }

    public static ProductRequest createKeyboardRequest() {

        ProductRequest keyboardRequest = new ProductRequest();
        keyboardRequest.setCategory(KEYBOARD_CATEGORY);
        keyboardRequest.setCurrency(CURRENCY);
        keyboardRequest.setAmount(AMOUNT);
        keyboardRequest.setStock(STOCK);
        keyboardRequest.setIssues(ISSUES);
        keyboardRequest.setKeyboardRequest(new KeyboardRequest(KEYBOARD_BRAND, TYPE_KEYBOARD));
        return keyboardRequest;
    }

    public static Product createHeadphone() {
        Product headphone = new Product();
        headphone.setId(ID_1);
        headphone.setCategory(HEADPHONE_CATEGORY);
        headphone.setCurrency(CURRENCY);
        headphone.setAmount(AMOUNT);
        Stock stock1 = new Stock();
        stock1.setId(ID_1);
        stock1.setNumberOfItems(STOCK);
        headphone.setStock(stock1);
        ProductDetail productDetail1 = new ProductDetail();
        productDetail1.setName("Brand");
        productDetail1.setValue(HEADPHONE_BRAND);

        ProductDetail productDetail2 = new ProductDetail();
        productDetail2.setName("Type");
        productDetail2.setValue(HEADPHONE_TYPE);

        HEADPHONE_DETAILS.addAll(Arrays.asList(productDetail1, productDetail2));
        headphone.setDetails(HEADPHONE_DETAILS);

        headphone.setIssue("something");

        return headphone;
    }

    public static Product createKeyboard() {
        Product keyboard = new Product();
        keyboard.setId(ID_2);
        keyboard.setCategory(KEYBOARD_CATEGORY);
        keyboard.setCurrency(CURRENCY);
        keyboard.setAmount(AMOUNT);
        Stock stock2 = new Stock();
        stock2.setId(ID_2);
        stock2.setNumberOfItems(STOCK);
        keyboard.setStock(stock2);
        ProductDetail productDetail1 = new ProductDetail();
        productDetail1.setName("Brand");
        productDetail1.setValue(KEYBOARD_BRAND);

        ProductDetail productDetail2 = new ProductDetail();
        productDetail2.setName("Type");
        productDetail2.setValue(TYPE_KEYBOARD);

        KEYBOARD_DETAILS.addAll(Arrays.asList(productDetail1, productDetail2));
        keyboard.setDetails(KEYBOARD_DETAILS);

        keyboard.setIssue("something");

        return keyboard;
    }

    public static Product createLaptop() {
        Product laptop = new Product();
        laptop.setId(ID_2);
        laptop.setCategory(LAPTOP_CATEGORY);
        laptop.setCurrency(CURRENCY);
        laptop.setAmount(AMOUNT);
        Stock stock2 = new Stock();
        stock2.setId(ID_2);
        stock2.setNumberOfItems(STOCK);
        laptop.setStock(stock2);

        ProductDetail productDetail1 = new ProductDetail();
        productDetail1.setName("brand");
        productDetail1.setValue(LAPTOP_BRAND);

        ProductDetail productDetail2 = new ProductDetail();
        productDetail2.setName("model");
        productDetail2.setValue(LAPTOP_MODEL);

        ProductDetail productDetail3 = new ProductDetail();
        productDetail3.setName("operatingSystem");
        productDetail3.setValue(LAPTOP_OPERATING_SYSTEM);

        ProductDetail productDetail4 = new ProductDetail();
        productDetail4.setName("memorySize");
        productDetail4.setValue("RAM" + MEMORY_SIZE + "GB");

        ProductDetail productDetail5 = new ProductDetail();
        productDetail5.setName("display");
        productDetail5.setValue(String.valueOf(DIAGONAL_DISPLAY));

        LAPTOP_DETAILS.addAll(Arrays.asList(productDetail1, productDetail2, productDetail3, productDetail4, productDetail5));
        laptop.setDetails(LAPTOP_DETAILS);
        laptop.setIssue("something");
        return laptop;
    }

    public static Product createPhone() {
        Product phone = new Product();
        phone.setId(ID_2);
        phone.setCategory(PHONE_CATEGORY);
        phone.setCurrency(CURRENCY);
        phone.setAmount(AMOUNT);
        Stock stock2 = new Stock();
        stock2.setId(ID_2);
        stock2.setNumberOfItems(STOCK);
        phone.setStock(stock2);

        ProductDetail productDetail1 = new ProductDetail();
        productDetail1.setName("brand");
        productDetail1.setValue(PHONE_BRAND);

        ProductDetail productDetail2 = new ProductDetail();
        productDetail2.setName("model");
        productDetail2.setValue(PHONE_MODEL);

        ProductDetail productDetail3 = new ProductDetail();
        productDetail3.setName("operatingSystem");
        productDetail3.setValue(PHONE_OPERATING_SYSTEM);

        ProductDetail productDetail4 = new ProductDetail();
        productDetail4.setName("memorySize");
        productDetail4.setValue("RAM" + MEMORY_SIZE + "GB");

        ProductDetail productDetail5 = new ProductDetail();
        productDetail5.setName("dualSim");
        productDetail5.setValue(String.valueOf(true));

        PHONE_DETAILS.addAll(Arrays.asList(productDetail1, productDetail2, productDetail3, productDetail4, productDetail5));
        phone.setDetails(PHONE_DETAILS);
        phone.setIssue("something");
        return phone;
    }
}