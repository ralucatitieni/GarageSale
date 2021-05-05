package com.portfolio.shop;

import com.portfolio.exception.InvalidCardException;
import com.portfolio.exception.InvalidEmailException;
import com.portfolio.model.product.Product;
import com.portfolio.model.product.accessory.Headphone;
import com.portfolio.model.product.accessory.Keyboard;
import com.portfolio.model.product.electronic.Laptop;
import com.portfolio.model.product.electronic.Phone;
import com.portfolio.model.purchase.Card;
import com.portfolio.model.purchase.CustomerDetails;
import com.portfolio.model.purchase.Purchase;
import com.portfolio.service.CardValidator;
import com.portfolio.service.EmailValidator;
import com.portfolio.service.ProductFilter;
import lombok.Getter;
import lombok.Setter;

import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

@Setter
@Getter
public class Shop {

    private static Scanner scanner = new Scanner(System.in);
    private List<Product> productList;
    private Purchase purchase = new Purchase(new ArrayList<>());

    public Shop(List<Product> productList) {
        this.productList = productList;
    }

    public void showMainMenu() {
        System.out.println("--------------------------------");
        System.out.println("       Endava Garage Sale       ");
        System.out.println("--------------------------------");
        System.out.println();
        System.out.println("            MAIN MENU           ");
        System.out.println("1. List all products");
        System.out.println("2. Filter by category");
        System.out.println("3. Sort by price");
        System.out.println("4. View cart");
        System.out.println("5. Exit");
        selectFromMainMenu();
    }

    public void selectFromMainMenu() {
        String menOption = scanner.next();
        switch (menOption) {
            case "1":
                System.out.println("List all products");
                productList.forEach(p -> System.out.println(p));
                System.out.println();
                purchaseOrGoBack();
                break;
            case "2":
                filterByCategory();
                break;
            case "3":
                System.out.println("Sort by price - in ascending order");
                System.out.println();
                List<Product> sortedByPrice = new ArrayList<>(productList);
                Collections.sort(sortedByPrice, Comparator.comparingDouble(p -> p.getPrice().getAmount()));
                sortedByPrice.forEach(p -> System.out.println(p));
                purchaseOrGoBack();
                break;
            case "4":
                System.out.println("View cart");
                System.out.println();
                if (purchase.getItemsInCart().size() == 0) {
                    System.out.println("Cart is empty. Go back and buy something.");
                    showMainMenu();
                }
                purchase.getItemsInCart().forEach(i -> System.out.println(i));
                System.out.println();
                System.out.println("Finish purchase or Review shopping cart \n (F to finish / any key to review)");
                String finishOrReview = scanner.next();
                if (finishOrReview.equalsIgnoreCase("F")) {
                    validatePurchase();
                } else {
                    reviewCart();
                }
                break;
            case "5":
                System.out.println("Are you sure you want to leave shop?(Y/any key to go back to Main Menu)");
                String exitOption = scanner.next();
                if (exitOption.equalsIgnoreCase("Y")) {
                    System.exit(0);
                } else {
                    showMainMenu();
                }
                break;
            default:
                System.out.println("Not a valid option. Select another one: ");
                selectFromMainMenu();
        }
    }

    public Product addToCart(List<Product> productList, String id) {
        for (Product product : productList) {
            if (product.getId().equalsIgnoreCase(id.trim())) {
                return product;
            }
        }
        return null;
    }

    public void removeItemFromCart() {
        purchase.getItemsInCart().forEach(p -> System.out.println(p.toString()));
        System.out.println("Enter product ID: ");
        String removedProductID = scanner.next();
        List<Product> removedFromCart = new ArrayList<>();
        for (Product product : purchase.getItemsInCart()) {
            if (product.getId().equalsIgnoreCase(removedProductID)) {
                removedFromCart.add(product);
            }
        }
        purchase.getItemsInCart().removeAll(removedFromCart);
        productList.addAll(removedFromCart);
    }

    public void reviewCart() {
        System.out.println("Do you want to add, remove item or finish purchase?\n " +
                "(A - add / R - remove / any key - finish purchase");
        String removeAddOption = scanner.next();
        if (removeAddOption.equalsIgnoreCase("A")) {
            showMainMenu();
        }
        if (removeAddOption.equalsIgnoreCase("R")) {
            removeItemFromCart();
            reviewCart();
        } else {
            validatePurchase();
        }
    }

    public void purchaseOrGoBack() {
        System.out.println("Purchase item? (Y/ any key to go back to Main Menu)");
        String buyOption = scanner.next().toUpperCase();
        if (buyOption.equalsIgnoreCase("Y")) {
            buyProduct();
        } else {
            showMainMenu();
        }
    }

    public void validatePurchase() {
        if (purchase.getItemsInCart().size() == 0) {
            System.out.println("Nothing in shopping cart.");
            System.out.println("Go back and buy");
            showMainMenu();
        } else {
            System.out.println("    VALIDATE SHOPPING    ");
            System.out.println("-------------------------");
            System.out.println("-------------------------");
            System.out.println("Product List");
            System.out.println("-------------------------");
            purchase.getItemsInCart().forEach(p -> System.out.println(p.toString()));
            System.out.println();
            System.out.println("Select currency: (E - EUR / U -USD / any key for RON)");
            String currency = scanner.next().toUpperCase();
            switch (currency) {
                case "E":
                    System.out.println("Total amount: " + purchase.getTotalAmountInEUR() + " EUR");
                    break;
                case "U":
                    System.out.println("Total amount: " + purchase.getTotalAmountInUSD() + " USD");
                    break;
                default:
                    System.out.println("Total amount: " + purchase.getTotalAmountInUSD() + " RON");
                    break;
            }

            System.out.println();
            System.out.println("Enter name on card: ");
            String firstName = scanner.next().toLowerCase();
            firstName = firstName.substring(0, 1).toUpperCase() + firstName.substring(1);
            String lastName = scanner.next().toLowerCase();
            lastName = lastName.substring(0, 1).toUpperCase() + lastName.substring(1);
            String email = checkEmail();
            CustomerDetails customerDetails = new CustomerDetails(firstName, lastName, email);
            Card card = checkCard();
            purchase.setCard(card);
            purchase.setCustomerDetails(customerDetails);
            System.out.println();
            showReceipt(purchase, currency);
        }
    }

    public void showReceipt(Purchase purchase, String currency) {
        System.out.println("---------------------------------------------");
        System.out.println("             PURCHASED LIST                  ");
        System.out.println("---------------------------------------------");
        System.out.println();
        purchase.getItemsInCart().forEach(i -> System.out.println(i.toString() + "\n"));
        System.out.println("---------------------------------------------");
        switch (currency) {
            case "E":
                System.out.println("TOTAL: " + purchase.getTotalAmountInEUR() + " EUR");
                break;
            case "U":
                System.out.println("TOTAL: " + purchase.getTotalAmountInUSD() + " USD");
                break;
            default:
                System.out.println("TOTAL: " + purchase.getTotalAmountInRON() + " RON");
                break;
        }
        System.out.println();
        System.out.println("Name: " + purchase.getCustomerDetails().getFirstName() + " " + purchase.getCustomerDetails().getLastName());
        System.out.println("Email: " + purchase.getCustomerDetails().getEmail());
        System.out.println("Card: XXXX" + String.valueOf(purchase.getCard().getCardNumber()).substring(12));
        System.out.println();
        System.out.println("Exit? (Y/any key to go back to main menu)");
        String optionAfterReceipt = scanner.next();
        if (optionAfterReceipt.equalsIgnoreCase("Y")) {
            System.exit(0);
        } else {
            purchase.getItemsInCart().clear();
            new Shop(productList).showMainMenu();
        }
    }

    public void showLaptopFilterOptions(List<Laptop> laptopList) {
        System.out.println("           LAPTOPS           ");
        System.out.println("-----------------------------");
        System.out.println("1. Filter by brand");
        System.out.println("2. Filter by model");
        System.out.println("3. Filter by operating system");
        System.out.println("4. Filter by diagonal");
        System.out.println("5. Go back to MAIN MENU");
        selectFromLaptopFilters(laptopList);
    }

    public void selectFromLaptopFilters(List<Laptop> laptopList) {
        String laptopFilter = scanner.next().toUpperCase();
        switch (laptopFilter) {
            case "1":
                System.out.println("Enter laptop brand: ");
                String brandOption = scanner.next();
                int availableLaptopBrand = 0;
                for (Laptop laptop : laptopList) {
                    if (laptop.getLaptopBrand().equalsIgnoreCase(brandOption)) {
                        availableLaptopBrand++;
                    }
                }
                if (availableLaptopBrand == 0) {
                    System.out.println("Brand not available");
                    showLaptopFilterOptions(laptopList);
                } else {
                    laptopList.stream().filter((ProductFilter.filterLaptopByBrand(brandOption))).
                            collect(Collectors.toList()).forEach(l -> System.out.println(l.toString()));
                    purchaseOrGoBack();
                }
                break;
            case "2":
                System.out.println("Enter laptop model: ");
                String modelOption = scanner.next();
                int availableLaptopModel = 0;
                for (Laptop laptop : laptopList) {
                    if (laptop.getLaptopModel().equalsIgnoreCase(modelOption)) {
                        availableLaptopModel++;
                    }
                }
                if (availableLaptopModel == 0) {
                    System.out.println("Model not available");
                    showLaptopFilterOptions(laptopList);
                } else {
                    laptopList.stream().filter((ProductFilter.filterLaptopByModel(modelOption))).
                            collect(Collectors.toList()).forEach(l -> System.out.println(l.toString()));
                    purchaseOrGoBack();
                }
                break;
            case "3":
                System.out.println("Enter laptop operatingSystem: ");
                String opOption = scanner.next();
                int availableLaptopOpSys = 0;
                for (Laptop laptop : laptopList) {
                    if (laptop.getOperatingSystem().toString().equalsIgnoreCase(opOption)) {
                        availableLaptopOpSys++;
                    }
                }
                if (availableLaptopOpSys == 0) {
                    System.out.println("Brand not available");
                    showLaptopFilterOptions(laptopList);
                } else {
                    laptopList.stream().filter((ProductFilter.filterLaptopByOperatingSystem(opOption))).
                            collect(Collectors.toList()).forEach(l -> System.out.println(l.toString()));
                    purchaseOrGoBack();
                }
                break;
            case "4":
                int minSize = 0;
                int maxSize = 1000;
                try {
                    System.out.println("Enter laptop minimum diagonal size: ");
                    minSize = Integer.parseInt(scanner.next());
                    System.out.println("Enter laptop maximum diagonal size: ");
                    maxSize = Integer.parseInt(scanner.next());
                } catch (Exception e) {
                    System.out.println("Values not valid. Printing all available laptops");

                }
                int availableLaptopSize = 0;
                for (Laptop laptop : laptopList) {
                    if (laptop.getDiagonalDisplay() >= minSize &&
                            laptop.getDiagonalDisplay() <= maxSize) {
                        availableLaptopSize++;
                    }
                }
                if (availableLaptopSize == 0) {
                    System.out.println("Brand not available");
                    showLaptopFilterOptions(laptopList);
                } else {
                    laptopList.stream().filter((ProductFilter.filterLaptopByDiagonal(minSize, maxSize))).
                            collect(Collectors.toList()).forEach(p -> System.out.println(p.toString()));
                    purchaseOrGoBack();
                }
                break;
            case "5":
                showMainMenu();
                break;
            default:
                System.out.println("Enter a valid selection");
                selectFromLaptopFilters(laptopList);
        }
    }

    public void showPhoneFilterOptions(List<Phone> phoneList) {
        System.out.println("           PHONES           ");
        System.out.println("-----------------------------");
        System.out.println("1. Filter by brand");
        System.out.println("2. Filter by model");
        System.out.println("3. Go back to MAIN MENU");
        selectFromPhoneFilters(phoneList);
    }

    public void selectFromPhoneFilters(List<Phone> phoneList) {
        String phoneFilter = scanner.next().toUpperCase();
        switch (phoneFilter) {
            case "1":
                System.out.println("Enter phone brand: ");
                String brandOption = scanner.next();
                int availablePhoneBrand = 0;
                for (Phone phone : phoneList) {
                    if (phone.getPhoneBrand().toString().equalsIgnoreCase(brandOption)) {
                        availablePhoneBrand++;
                    }
                }
                if (availablePhoneBrand == 0) {
                    System.out.println("Brand not available");
                    showPhoneFilterOptions(phoneList);
                } else {
                    phoneList.stream().filter((ProductFilter.filterPhoneByBrand(brandOption))).
                            collect(Collectors.toList()).forEach(l -> System.out.println(l.toString()));
                    purchaseOrGoBack();
                }
                break;
            case "2":
                System.out.println("Enter phone model: ");
                String modelOption = scanner.next();
                int availablePhoneModel = 0;
                for (Phone phone : phoneList) {
                    if (phone.getPhoneModel().equalsIgnoreCase(modelOption)) {
                        availablePhoneModel++;
                    }
                }
                if (availablePhoneModel == 0) {
                    System.out.println("Model not available");
                    showPhoneFilterOptions(phoneList);
                } else {
                    phoneList.stream().filter((ProductFilter.filterPhoneByModel(modelOption))).
                            collect(Collectors.toList()).forEach(l -> System.out.println(l.toString()));
                    purchaseOrGoBack();
                }
                break;
            case "3":
                showMainMenu();
                break;
            default:
                System.out.println("Enter a valid selection");
                selectFromPhoneFilters(phoneList);
        }
    }

    public void showKeyboardFilterOptions(List<Keyboard> keyboardList) {
        System.out.println("           KEYBOARDS           ");
        System.out.println("-----------------------------");
        System.out.println("1. Filter by brand");
        System.out.println("2. Go back to MAIN MENU");
        selectFromKeyboardFilters(keyboardList);
    }

    public void selectFromKeyboardFilters(List<Keyboard> keyboardList) {
        String phoneFilter = scanner.next().toUpperCase();
        switch (phoneFilter) {
            case "1":
                System.out.println("Enter phone brand: ");
                String brandOption = scanner.next();
                int availableKeyboardBrand = 0;
                for (Keyboard keyboard : keyboardList) {
                    if (keyboard.getKeyboardBrand().equalsIgnoreCase(brandOption)) {
                        availableKeyboardBrand++;
                    }
                }
                if (availableKeyboardBrand == 0) {
                    System.out.println("Brand not available");
                    showKeyboardFilterOptions(keyboardList);
                } else {
                    keyboardList.stream().filter((ProductFilter.filterKeyboardByBrand(brandOption))).
                            collect(Collectors.toList()).forEach(l -> System.out.println(l.toString()));
                    purchaseOrGoBack();
                }
                break;
            case "2":
                showMainMenu();
                break;
            default:
                System.out.println("Enter a valid selection");
                selectFromKeyboardFilters(keyboardList);
        }
    }

    public void showHeadphoneFilterOptions(List<Headphone> headphoneList) {
        System.out.println("           HEADPHONES           ");
        System.out.println("-----------------------------");
        System.out.println("1. Filter by brand");
        System.out.println("2. Go back to MAIN MENU");
        selectFromHeadphoneFilters(headphoneList);
    }

    public void selectFromHeadphoneFilters(List<Headphone> headphoneList) {
        String headphoneFilter = scanner.next().toUpperCase();
        switch (headphoneFilter) {
            case "1":
                System.out.println("Enter phone brand: ");
                String brandOption = scanner.next();
                int availableHeadphoneBrand = 0;
                for (Headphone headphone : headphoneList) {
                    if (headphone.getHeadphoneBrand().equalsIgnoreCase(brandOption)) {
                        availableHeadphoneBrand++;
                    }
                }
                if (availableHeadphoneBrand == 0) {
                    System.out.println("Brand not available");
                    showHeadphoneFilterOptions(headphoneList);
                } else {
                    headphoneList.stream().filter((ProductFilter.filterHeadphoneByBrand(brandOption))).
                            collect(Collectors.toList()).forEach(l -> System.out.println(l.toString()));
                    purchaseOrGoBack();
                }
                break;
            case "2":
                showMainMenu();
                break;
            default:
                System.out.println("Enter a valid selection");
                selectFromHeadphoneFilters(headphoneList);
        }
    }

    public void buyProduct() {
        System.out.println();
        System.out.println("Enter product id: ");
        String id = scanner.next();
        try {
            for (Product product : purchase.getItemsInCart()) {
                if (product.getProductCategory().equals(returnProduct(id).getProductCategory())) {
                    System.out.println("You already made a purchase from this category. Look for another product.");
                    showMainMenu();
                }
            }
        } catch (NullPointerException e) {
            buyProduct();
        }
        purchase.getItemsInCart().add(addToCart(productList, id));
        System.out.println("Items in cart now");
        purchase.getItemsInCart().forEach(i -> System.out.println(i));
        productList.removeAll(purchase.getItemsInCart());

        System.out.println("Do you want to make another purchase?(Y/any key- go back to main menu)");
        String optionAnother = scanner.next().toUpperCase();
        if (optionAnother.equalsIgnoreCase("Y")) {
            buyProduct();
        } else {
            showMainMenu();
        }
    }

    public Product returnProduct(String id) {
        for (Product product : productList) {
            if (product.getId().equalsIgnoreCase(id)) {
                return product;
            }
        }
        return null;
    }

    public String checkEmail() {
        System.out.println("Enter email: ");
        String emailName = scanner.next().toLowerCase();
        try {
            EmailValidator.validateEmail(emailName);
        } catch (InvalidEmailException e) {
            System.out.println("Invalid email: " + e.getMessage());
            checkEmail();
        }
        return emailName;
    }

    public Card checkCard() {
        System.out.println("Enter card number: ");
        String cardNumber = scanner.next();
        System.out.println("Enter card CVV: ");
        String cardCvv = scanner.next();
        System.out.println("Enter month and year");
        int month = Integer.parseInt(scanner.next());
        int year = Integer.parseInt(scanner.next());
        if (month < 1 || month > 12 || year < 1) {
            System.out.println("Invalid date");
            checkCard();
        }
        YearMonth expireDate = YearMonth.of(year, month + 1);
        Card card = new Card(cardNumber, cardCvv, expireDate);
        try {
            CardValidator.validateCard(card);
        } catch (InvalidCardException e) {
            System.out.println("Invalid card: " + e.getMessage());
            checkCard();
        }
        return card;
    }

    public void filterByCategory() {
        System.out.println("Filter by category");
        System.out.println("LAPTOP / PHONE / KEYBOARD / HEADPHONE");
        String categoryOption = scanner.next().toUpperCase();
        if (categoryOption.contains("LAPTOP") ||
                categoryOption.contains("PHONE") ||
                categoryOption.contains("KEYBOARD") ||
                categoryOption.contains("HEADPHONE")) {
            productList.stream().filter(ProductFilter.filterByCategory(categoryOption)).
                    collect(Collectors.toList()).forEach(p -> System.out.println(p.toString()));
            System.out.println("CONTINUE FILTERING (F - filter \n P - purchase item \n " +
                    "any key - go back to main Menu)");
            String continueOption = scanner.next().toUpperCase();
            switch (continueOption) {
                case "F":
                    System.out.println("Do more filtering");
                    switch (categoryOption) {
                        case "LAPTOP":
                            List<Laptop> laptopList = new ArrayList<>();
                            for (Product product : productList.stream().filter(ProductFilter.filterByCategory(categoryOption)).
                                    collect(Collectors.toList())) {
                                if (product instanceof Laptop) {
                                    laptopList.add((Laptop) product);
                                }
                            }
                            System.out.println(laptopList.size() + " Products available");
                            System.out.println();
                            showLaptopFilterOptions(laptopList);
                            break;
                        case "PHONE":
                            List<Phone> phoneList = new ArrayList<>();
                            for (Product product : productList.stream().filter(ProductFilter.filterByCategory(categoryOption)).
                                    collect(Collectors.toList())) {
                                if (product instanceof Phone) {
                                    phoneList.add((Phone) product);
                                }
                            }
                            System.out.println(phoneList.size() + " Products available");
                            System.out.println();
                            showPhoneFilterOptions(phoneList);
                            break;
                        case "KEYBOARD":
                            List<Keyboard> keyboardList = new ArrayList<>();
                            for (Product product : productList.stream().filter(ProductFilter.filterByCategory(categoryOption)).
                                    collect(Collectors.toList())) {
                                if (product instanceof Keyboard) {
                                    keyboardList.add((Keyboard) product);
                                }
                            }
                            System.out.println(keyboardList.size() + " Products available");
                            System.out.println();
                            showKeyboardFilterOptions(keyboardList);
                            break;
                        case "HEADPHONE":
                            List<Headphone> headphoneList = new ArrayList<>();
                            for (Product product : productList.stream().filter(ProductFilter.filterByCategory(categoryOption)).
                                    collect(Collectors.toList())) {
                                if (product instanceof Headphone) {
                                    headphoneList.add((Headphone) product);
                                }
                            }
                            System.out.println(headphoneList.size() + " Products available");
                            System.out.println();
                            showHeadphoneFilterOptions(headphoneList);
                            break;
                    }
                    break;
                case "P":
                    purchaseOrGoBack();
                    break;
                default:
                    showMainMenu();
                    break;
            }
        } else {
            System.out.println("Not a valid category");
            filterByCategory();
        }
    }
}
