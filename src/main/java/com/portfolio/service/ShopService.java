package com.portfolio.service;

import com.portfolio.model.product.Product;
import com.portfolio.model.product.Stock;
import com.portfolio.model.product.accessory.Headphone;
import com.portfolio.model.product.accessory.Keyboard;
import com.portfolio.model.product.electronic.Laptop;
import com.portfolio.model.product.electronic.Phone;
import com.portfolio.repository.DisplayProductRepository;
import com.portfolio.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ShopService {

    private DisplayProductRepository displayProductRepository;
    private ProductFilter productFilter;
    private StockRepository stockRepository;

    @Autowired
    public ShopService(DisplayProductRepository displayProductRepository,
                       ProductFilter productFilter, StockRepository stockRepository) {
        this.displayProductRepository = displayProductRepository;
        this.productFilter = productFilter;
        this.stockRepository = stockRepository;
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
    }

    public void showLaptopFilterOptions() {
        System.out.println("           LAPTOPS           ");
        System.out.println("-----------------------------");
        System.out.println("1. Filter by brand");
        System.out.println("2. Filter by model");
        System.out.println("3. Filter by operating system");
        System.out.println("4. Filter by diagonal");
        System.out.println("5. Go back to MAIN MENU");

    }

    public void showPhoneFilterOptions() {
        System.out.println("           PHONES           ");
        System.out.println("-----------------------------");
        System.out.println("1. Filter by brand");
        System.out.println("2. Filter by model");
        System.out.println("3. Go back to MAIN MENU");
    }

    public void showKeyboardFilterOptions() {
        System.out.println("           KEYBOARDS           ");
        System.out.println("-----------------------------");
        System.out.println("1. Filter by brand");
        System.out.println("2. Go back to MAIN MENU");
    }

    public void showHeadphoneFilterOptions() {
        System.out.println("           HEADPHONES           ");
        System.out.println("-----------------------------");
        System.out.println("1. Filter by brand");
        System.out.println("2. Go back to MAIN MENU");
    }

    public void printProductListWStock() {
        displayProductRepository.getAllDisplayProducts().forEach(p -> System.out.println(p));
    }

    public void printProductByCategoryWStock(String categoryOption) {
        for (Product product : productFilter.filterByCategory(categoryOption)) {
            for (Stock stock : stockRepository.getStockList()) {
                if (product.getId().equalsIgnoreCase(stock.getProductId())) {
                    System.out.println(product + ", Number of Items: " + stock.getNumberOfItems());
                }
            }
        }
    }

    public void printLaptopByBrandListWStock(String brandOption) {
        for (Product laptop : productFilter.filterLaptopByBrand(brandOption)) {
            for (Stock stock : stockRepository.getStockList()) {
                if (laptop.getId().equalsIgnoreCase(stock.getProductId())) {
                    System.out.println(laptop + ", Number of Items: " + stock.getNumberOfItems());
                }
            }
        }
    }

    public void printLaptopByModelListWStock(String modelOption) {
        for (Laptop laptop : productFilter.filterLaptopByModel(modelOption)) {
            for (Stock stock : stockRepository.getStockList()) {
                if (laptop.getId().equalsIgnoreCase(stock.getProductId())) {
                    System.out.println(laptop + ", Number of Items: " + stock.getNumberOfItems());
                }
            }
        }
    }

    public void printLaptopByOSListWStock(String opOption) {
        for (Laptop laptop : productFilter.filterLaptopByOperatingSystem(opOption)) {
            for (Stock stock : stockRepository.getStockList()) {
                if (laptop.getId().equalsIgnoreCase(stock.getProductId())) {
                    System.out.println(laptop + ", Number of Items: " + stock.getNumberOfItems());
                }
            }
        }
    }

    public void printLaptopByDiagonalListWStock(int minSize, int maxSize) {
        for (Laptop laptop : productFilter.filterLaptopByDiagonal(minSize, maxSize)) {
            for (Stock stock : stockRepository.getStockList()) {
                if (laptop.getId().equalsIgnoreCase(stock.getProductId())) {
                    System.out.println(laptop + ", Number of Items: " + stock.getNumberOfItems());
                }
            }
        }
    }

    public void printPhoneByBrandListWStock(String brandOption) {
        for (Phone phone : productFilter.filterPhoneByBrand(brandOption)) {
            for (Stock stock : stockRepository.getStockList()) {
                if (phone.getId().equalsIgnoreCase(stock.getProductId())) {
                    System.out.println(phone + ", Number of Items: " + stock.getNumberOfItems());
                }
            }
        }
    }

    public void printPhoneByModelListWStock(String modelOption) {
        for (Phone phone : productFilter.filterPhoneByModel(modelOption)) {
            for (Stock stock : stockRepository.getStockList()) {
                if (phone.getId().equalsIgnoreCase(stock.getProductId())) {
                    System.out.println(phone + ", Number of Items: " + stock.getNumberOfItems());
                }
            }
        }
    }

    public void printKeyboardBrandListWStock(String brandOption) {
        for (Keyboard keyboard : productFilter.filterKeyboardByBrand(brandOption)) {
            for (Stock stock : stockRepository.getStockList()) {
                if (keyboard.getId().equalsIgnoreCase(stock.getProductId())) {
                    System.out.println(keyboard + ", Number of Items: " + stock.getNumberOfItems());
                }
            }
        }
    }

    public void printHeadphoneBrandListWStock(String brandOption) {
        for (Headphone headphone : productFilter.filterHeadphoneByBrand(brandOption)) {
            for (Stock stock : stockRepository.getStockList()) {
                if (headphone.getId().equalsIgnoreCase(stock.getProductId())) {
                    System.out.println(headphone + ", Number of Items: " + stock.getNumberOfItems());
                }
            }
        }
    }
}
