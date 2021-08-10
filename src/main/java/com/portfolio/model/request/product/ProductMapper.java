package com.portfolio.model.request.product;

import com.portfolio.entity.product.Product;
import com.portfolio.entity.product.ProductDetail;
import com.portfolio.entity.product.Stock;
import com.portfolio.repository.ProductRepository;
import com.portfolio.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class ProductMapper {

    private ProductRepository productRepository;
    private StockRepository stockRepository;

    @Autowired
    public ProductMapper(ProductRepository productRepository, StockRepository stockRepository) {
        this.productRepository = productRepository;
        this.stockRepository = stockRepository;
    }

    public Product mapToProduct(ProductRequest productRequest) {

        Product newProduct = createProduct(productRequest);
        Stock stock = createStock(productRequest, newProduct);
        newProduct.setStock(stock);
        List<ProductDetail> details;
        switch (productRequest.getCategory().toUpperCase()) {
            case "LAPTOP":
                details = createLaptopDetails(productRequest, newProduct);
                newProduct.setDetails(details);
                break;
            case "PHONE":
                details = createPhoneDetails(productRequest, newProduct);
                newProduct.setDetails(details);
                break;
            case "HEADPHONE":
                details = createHeadphoneDetails(productRequest, newProduct);
                newProduct.setDetails(details);
                break;
            case "KEYBOARD":
                details = createKeyboardDetails(productRequest, newProduct);
                newProduct.setDetails(details);
                break;
        }

        for (Product product : productRepository.findAll()) {
            if (product.equals(newProduct)) {
                Stock newStock = stockRepository.findById(product.getId()).get();
                newStock.setNumberOfItems(product.getStock().getNumberOfItems() + productRequest.getStock());
                newProduct = product;
                newProduct.setStock(newStock);

            }
        }

        return newProduct;
    }

    private List<ProductDetail> createLaptopDetails(ProductRequest productRequest, Product product) {
        List<ProductDetail> productDetailList = new ArrayList<>();
        ProductDetail detail1 = createProductDetail("brand", productRequest.getLaptopRequest().getBrand(), product);
        ProductDetail detail2 = createProductDetail("model", productRequest.getLaptopRequest().getModel(), product);
        ProductDetail detail3 = createProductDetail("operatingSystem", productRequest.getLaptopRequest().getOperatingSystem(), product);
        ProductDetail detail4 = createProductDetail("memorySize", "RAM" + productRequest.getLaptopRequest().getMemorySize() + "GB", product);
        ProductDetail detail5 = createProductDetail("display", String.valueOf(productRequest.getLaptopRequest().getDiagonalDisplay()), product);
        productDetailList.addAll(Arrays.asList(detail1, detail2, detail3, detail4, detail5));

        return productDetailList;
    }

    private List<ProductDetail> createPhoneDetails(ProductRequest productRequest, Product product) {
        List<ProductDetail> productDetailList = new ArrayList<>();
        ProductDetail detail1 = createProductDetail("brand", productRequest.getPhoneRequest().getBrand(), product);
        ProductDetail detail2 = createProductDetail("model", productRequest.getPhoneRequest().getModel(), product);
        ProductDetail detail3 = createProductDetail("operatingSystem", productRequest.getPhoneRequest().getOperatingSystem(), product);
        ProductDetail detail4 = createProductDetail("memorySize", "RAM" + productRequest.getPhoneRequest().getMemorySize() + "GB", product);
        ProductDetail detail5 = createProductDetail("dualSim", "RAM" + String.valueOf(productRequest.getPhoneRequest().getDualSim()), product);
        productDetailList.addAll(Arrays.asList(detail1, detail2, detail3, detail4, detail5));

        return productDetailList;
    }

    private List<ProductDetail> createHeadphoneDetails(ProductRequest productRequest, Product product) {
        List<ProductDetail> productDetailList = new ArrayList<>();
        ProductDetail detail1 = createProductDetail("brand", productRequest.getHeadphoneRequest().getBrand(), product);
        ProductDetail detail2 = createProductDetail("type", productRequest.getHeadphoneRequest().getType(), product);
        productDetailList.addAll(Arrays.asList(detail1, detail2));

        return productDetailList;
    }

    private List<ProductDetail> createKeyboardDetails(ProductRequest productRequest, Product product) {
        List<ProductDetail> productDetailList = new ArrayList<>();
        ProductDetail detail1 = createProductDetail("brand", productRequest.getKeyboardRequest().getBrand(), product);
        ProductDetail detail2 = createProductDetail("type", productRequest.getKeyboardRequest().getType(), product);
        productDetailList.addAll(Arrays.asList(detail1, detail2));

        return productDetailList;
    }

    private ProductDetail createProductDetail(String detailType, String detailName, Product product) {
        ProductDetail detail = new ProductDetail();
        detail.setName(detailType);
        detail.setValue(detailName);
        detail.setProduct(product);
        return detail;
    }

    private Product createProduct(ProductRequest productRequest) {
        Product product = new Product();
        product.setCategory(productRequest.getCategory());
        product.setCurrency(productRequest.getCurrency());
        product.setAmount(productRequest.getAmount());
        product.setIssue(createIssues(productRequest));
        return product;
    }

    private Stock createStock(ProductRequest productRequest, Product product) {
        Stock stock = new Stock();
        stock.setNumberOfItems(productRequest.getStock());
        stock.setProduct(product);
        return stock;
    }

    private String createIssues(ProductRequest productRequest) {
        StringBuilder builder = new StringBuilder();
        productRequest.getIssues().forEach(i -> builder.append(i + "/"));
        builder.deleteCharAt(builder.length() - 1);
        return builder.toString();
    }
}