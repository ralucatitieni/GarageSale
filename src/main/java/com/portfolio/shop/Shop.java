package com.portfolio.shop;

import com.portfolio.model.product.Product;
import com.portfolio.model.product.Stock;
import com.portfolio.model.purchase.Card;
import com.portfolio.model.purchase.CustomerDetails;
import com.portfolio.model.purchase.Order;
import com.portfolio.repository.OrderRepository;
import com.portfolio.repository.StockRepository;
import com.portfolio.service.CustomerService;
import com.portfolio.service.OrderService;
import com.portfolio.service.ProductFilter;
import com.portfolio.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.YearMonth;
import java.util.Scanner;

@Component
public class Shop {
    private static final Scanner scanner = new Scanner(System.in);

    private final CustomerService customerService;
    private final OrderService orderService;
    private final ProductFilter productFilter;
    private final OrderRepository orderRepository;
    private final StockRepository stockRepository;
    private final ShopService shopService;
    private Order order;

    @Autowired
    public Shop(CustomerService customerService, ProductFilter productFilter, OrderRepository orderRepository,
                StockRepository stockRepository, OrderService orderService, ShopService shopService) {
        this.customerService = customerService;
        this.productFilter = productFilter;
        this.orderRepository = orderRepository;
        this.stockRepository = stockRepository;
        this.orderService = orderService;
        this.shopService = shopService;
    }

    public void start() {
        order = new Order();
        showHomePage();

    }

    public void showHomePage() {
        shopService.showMainMenu();
        selectFromMainMenu();
    }

    public void selectFromMainMenu() {
        String menOption = scanner.next();
        switch (menOption) {
            case "1":
                System.out.println("List all products");
                shopService.printProductListWStock();
                System.out.println();
                purchaseOrGoBack();
                break;
            case "2":
                filterByCategory();
                break;
            case "3":
                System.out.println("Sort by price - in ascending order");
                System.out.println();
                productFilter.sortByPrice().forEach(p -> System.out.println(p));
                purchaseOrGoBack();
                break;
            case "4":
                System.out.println("View cart");
                System.out.println();
                if (order.getItemsInCart().size() == 0) {
                    System.out.println("Cart is empty. Go back and buy something.");
                    showHomePage();
                }
                order.getItemsInCart().forEach(i -> System.out.println(i));
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
                    showHomePage();
                }
                break;
            default:
                System.out.println("Not a valid option. Select another one: ");
                selectFromMainMenu();
        }
    }

    public void filterByCategory() {
        System.out.println("Filter by category");
        System.out.println("LAPTOP / PHONE / KEYBOARD / HEADPHONE");
        String categoryOption = scanner.next().toUpperCase();
        if (categoryOption.contains("LAPTOP") ||
                categoryOption.contains("PHONE") ||
                categoryOption.contains("KEYBOARD") ||
                categoryOption.contains("HEADPHONE")) {
            shopService.printProductByCategoryWStock(categoryOption);
            if (productFilter.filterByCategory(categoryOption).size() == 0) {
                System.out.println("No more items in this category. Go back and look for something else");
                showHomePage();
            }
            System.out.println("CONTINUE FILTERING (F - filter \n P - purchase item \n " +
                    "any key - go back to main Menu)");
            String continueOption = scanner.next().toUpperCase();
            switch (continueOption) {
                case "F":
                    System.out.println();
                    showFilteringOptions(categoryOption);
                    break;
                case "P":
                    purchaseOrGoBack();
                    break;
                default:
                    showHomePage();
                    break;
            }
        } else {
            System.out.println("Not a valid category");
            filterByCategory();
        }
    }

    public void showFilteringOptions(String categoryOption) {
        switch (categoryOption) {
            case "LAPTOP":
                shopService.showLaptopFilterOptions();
                selectFromLaptopFilters(categoryOption);
                break;
            case "PHONE":
                shopService.showPhoneFilterOptions();
                selectFromPhoneFilters(categoryOption);
                break;
            case "KEYBOARD":
                shopService.showKeyboardFilterOptions();
                selectFromKeyboardFilters(categoryOption);
                break;
            case "HEADPHONE":
                shopService.showHeadphoneFilterOptions();
                selectFromHeadphoneFilters(categoryOption);
                break;

        }
    }

    public void selectFromLaptopFilters(String category) {
        String laptopFilter = scanner.next().toUpperCase();
        switch (laptopFilter) {
            case "1":
                System.out.println("Enter laptop brand: ");
                String brandOption = scanner.next();
                if (productFilter.filterLaptopByBrand(brandOption).size() == 0) {
                    System.out.println("Brand not available");
                    showFilteringOptions(category);
                }
                shopService.printLaptopByBrandListWStock(brandOption);
                purchaseOrGoBack();

                break;
            case "2":
                System.out.println("Enter laptop model: ");
                String modelOption = scanner.next();
                if (productFilter.filterLaptopByModel(modelOption).size() == 0) {
                    System.out.println("Model not available");
                    showFilteringOptions(category);
                } else {
                    shopService.printLaptopByModelListWStock(modelOption);
                    purchaseOrGoBack();
                }
                break;
            case "3":
                System.out.println("Enter laptop operatingSystem: ");
                String opOption = scanner.next();
                if (productFilter.filterLaptopByOperatingSystem(opOption).size() == 0) {
                    System.out.println("Brand not available");
                    showFilteringOptions(category);
                } else {
                    shopService.printLaptopByOSListWStock(opOption);
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
                } catch (NumberFormatException e) {
                    System.out.println("Values not valid. Printing all available laptops");
                    productFilter.filterByCategory("LAPTOP").forEach(p -> System.out.println(p));
                    System.out.println();
                    purchaseOrGoBack();
                }
                if (productFilter.filterLaptopByDiagonal(minSize, maxSize).size() == 0) {
                    System.out.println("Brand not available");
                    showFilteringOptions(category);
                } else {
                    shopService.printLaptopByDiagonalListWStock(minSize, maxSize);
                    purchaseOrGoBack();
                }
                break;
            case "5":
                showHomePage();
                break;
            default:
                System.out.println("Enter a valid selection");
                selectFromLaptopFilters(category);
        }

    }

    public void selectFromPhoneFilters(String category) {
        String phoneFilter = scanner.next().toUpperCase();
        switch (phoneFilter) {
            case "1":
                System.out.println("Enter phone brand: ");
                String brandOption = scanner.next();
                if (productFilter.filterPhoneByBrand(brandOption).size() == 0) {
                    System.out.println("Brand not available");
                    showFilteringOptions(category);
                } else {
                    shopService.printPhoneByBrandListWStock(brandOption);
                    purchaseOrGoBack();
                }
                break;
            case "2":
                System.out.println("Enter phone model: ");
                String modelOption = scanner.next();
                if (productFilter.filterPhoneByModel(modelOption).size() == 0) {
                    System.out.println("Model not available");
                    showFilteringOptions(category);
                } else {
                    shopService.printPhoneByModelListWStock(modelOption);
                    purchaseOrGoBack();
                }
                break;
            case "3":
                showHomePage();
                break;
            default:
                System.out.println("Enter a valid selection");

                selectFromPhoneFilters(category);
        }
    }

    public void selectFromKeyboardFilters(String category) {
        String phoneFilter = scanner.next().toUpperCase();
        switch (phoneFilter) {
            case "1":
                System.out.println("Enter keyboard brand: ");
                String brandOption = scanner.next();
                if (productFilter.filterKeyboardByBrand(brandOption).size() == 0) {
                    System.out.println("Brand not available");
                    showFilteringOptions(category);
                } else {
                    shopService.printKeyboardBrandListWStock(brandOption);
                    purchaseOrGoBack();
                }
                break;
            case "2":
                showHomePage();
                break;
            default:
                System.out.println("Enter a valid selection");
                selectFromKeyboardFilters(category);
        }
    }

    public void selectFromHeadphoneFilters(String category) {
        String headphoneFilter = scanner.next().toUpperCase();
        switch (headphoneFilter) {
            case "1":
                System.out.println("Enter phone brand: ");
                String brandOption = scanner.next();
                if (productFilter.filterHeadphoneByBrand(brandOption).size() == 0) {
                    System.out.println("Brand not available");
                    showFilteringOptions(category);
                } else {
                    shopService.printHeadphoneBrandListWStock(brandOption);
                    purchaseOrGoBack();
                }
                break;
            case "2":
                showHomePage();
                break;
            default:
                System.out.println("Enter a valid selection");
                selectFromHeadphoneFilters(category);
        }
    }

    public void purchaseOrGoBack() {
        System.out.println("Purchase item? (Y/ any key to go back to Main Menu)");
        String buyOption = scanner.next().toUpperCase();
        if (buyOption.equalsIgnoreCase("Y")) {
            buyProduct();
        } else {
            showHomePage();
        }
    }

    public void buyProduct() {
        System.out.println();
        System.out.println("Enter product id: ");
        String id = scanner.next();
        for (Stock stock : stockRepository.getStockList()) {
            if (stock.getProductId().equalsIgnoreCase(id)) {
                if (stock.getNumberOfItems() == 0) {
                    System.out.println("This item is out of stock. Look for another product");
                    showHomePage();
                }
            }
        }
        try {
            for (Product product : order.getItemsInCart()) {
                if (product.getProductCategory().equals(productFilter.returnProduct(id).getProductCategory())) {
                    System.out.println("You already made a purchase from this category. Look for another product.");
                    showHomePage();
                }
            }
        } catch (NullPointerException e) {
            buyProduct();
        }
        System.out.println("Items in cart now");
        orderService.addToCart(id, order).forEach(p -> System.out.println(p));
        System.out.println("Do you want to make another purchase?(Y/any key- go back to main menu)");
        String optionAnother = scanner.next().toUpperCase();
        if (optionAnother.equalsIgnoreCase("Y")) {
            buyProduct();
        } else {
            showHomePage();
        }
    }

    public void reviewCart() {
        System.out.println("Do you want to add, remove item or finish purchase?\n " +
                "(A - add / R - remove / any key - finish purchase");
        String removeAddOption = scanner.next();
        if (removeAddOption.equalsIgnoreCase("A")) {
            showHomePage();
        }
        if (removeAddOption.equalsIgnoreCase("R")) {
            System.out.println("Enter product ID: ");
            String removedProductID = scanner.next();
            System.out.println("Remaining items in cart: ");
            orderService.removeItemFromCart(removedProductID, order).forEach(p -> System.out.println(p));
            reviewCart();
        } else {
            validatePurchase();
        }
    }

    public void validatePurchase() {
        if (order.getItemsInCart().size() == 0) {
            System.out.println("Nothing in shopping cart.");
            System.out.println("Go back and buy");
            showHomePage();
        } else {
            System.out.println("    VALIDATE SHOPPING    ");
            System.out.println("-------------------------");
            System.out.println("-------------------------");
            System.out.println("Product List");
            System.out.println("-------------------------");
            order.getItemsInCart().forEach(p -> System.out.println(p.toString()));
            System.out.println();
            System.out.println("Select currency: (E - EUR / U -USD / any key for RON)");
            String currency = scanner.next().toUpperCase();
            switch (currency) {
                case "E":
                    System.out.println("Total amount: " + orderService.getTotalAmountInEUR(order) + " EUR");
                    break;
                case "U":
                    System.out.println("Total amount: " + orderService.getTotalAmountInUSD(order) + " USD");
                    break;
                default:
                    System.out.println("Total amount: " + orderService.getTotalAmountInRON(order) + " RON");
                    break;
            }

            System.out.println();
            System.out.println("Enter name on card: ");
            String firstName = scanner.next().toLowerCase();
            firstName = firstName.substring(0, 1).toUpperCase() + firstName.substring(1);
            String lastName = scanner.next().toLowerCase();
            lastName = lastName.substring(0, 1).toUpperCase() + lastName.substring(1);
            String email = addEmail();
            order.setCard(addCard());
            CustomerDetails customerDetails = new CustomerDetails(firstName, lastName, email);
            order.setCustomerDetails(customerDetails);
            System.out.println();
            order.setPayed(true);
            orderRepository.registerOrder(order);
            showReceipt(currency);
        }
    }

    public void showReceipt(String currency) {
        System.out.println("---------------------------------------------");
        System.out.println("             PURCHASED LIST                  ");
        System.out.println("---------------------------------------------");
        System.out.println();
        order.getItemsInCart().forEach(i -> System.out.println(i.toString() + "\n"));
        System.out.println("---------------------------------------------");
        switch (currency) {
            case "E":
                System.out.println("TOTAL: " + orderService.getTotalAmountInEUR(order) + " EUR");
                break;
            case "U":
                System.out.println("TOTAL: " + orderService.getTotalAmountInUSD(order) + " USD");
                break;
            default:
                System.out.println("TOTAL: " + orderService.getTotalAmountInRON(order) + " RON");
                break;
        }

        System.out.println();
        System.out.println(order);
        System.out.println();
        System.out.println("All orders: ");
        orderRepository.getAllOrders().forEach(p -> System.out.println(p));
        if (order.isPayed()) {
            start();
        }
    }

    public String addEmail() {
        System.out.println("Enter email: ");
        String email = scanner.next().toLowerCase();
        if (!customerService.checkEmail(email)) {
            addEmail();
        }
        return email;
    }

    public Card addCard() {
        System.out.println("Enter card number: ");
        String cardNumber = scanner.next();
        System.out.println("Enter card CVV: ");
        String cardCvv = scanner.next();
        System.out.println("Enter month and year");
        int month = Integer.parseInt(scanner.next());
        int year = Integer.parseInt(scanner.next());
        if (month < 1 || month > 12 || year < 1) {
            System.out.println("Invalid date");
            addCard();
        }
        YearMonth expireDate = YearMonth.of(year, month + 1);
        if (!customerService.checkCard(cardNumber, cardCvv, expireDate)) {
            addCard();
        }
        return new Card(cardNumber, cardCvv, expireDate);
    }

}