package com.portfolio.model.request.order;

import com.portfolio.model.product.Product;
import com.portfolio.model.product.Stock;
import com.portfolio.model.purchase.CustomerDetails;
import com.portfolio.model.purchase.Order;
import com.portfolio.repository.OrderRepository;
import com.portfolio.repository.ProductRepository;
import com.portfolio.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class OrderMapper {

    private StockRepository stockRepository;
    private ProductRepository productRepository;
    private OrderRepository orderRepository;

    @Autowired
    public OrderMapper(StockRepository stockRepository, ProductRepository productRepository,
                       OrderRepository orderRepository) {
        this.stockRepository = stockRepository;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

    public Order createOrder(OrderRequest orderRequest) {
        Order order = new Order();
        CustomerDetails customerDetails = createCustomerDetails(orderRequest.getCustomerDetailsRequest());
        order.setCustomerDetails(customerDetails);
        List<Product> productsInCart = createProductListInCart(orderRequest.getProductIds());
        order.setItemsInCart(productsInCart);
        orderRepository.getAllOrders().add(order);
        return order;
    }

    public void updateStock(Set<String> ids) {
        for (String id : ids) {
            Stock stock = stockRepository.getStockById(id);
            stock.setNumberOfItems(stock.getNumberOfItems() - 1);
        }
    }

    private List<Product> createProductListInCart(Set<String> ids) {
        List<Product> itemsInCart = new ArrayList<>();
        for (String id : ids) {
            Product product = productRepository.getProductById(id);
            itemsInCart.add(product);
        }
        return itemsInCart;
    }

    private CustomerDetails createCustomerDetails(CustomerDetailsRequest customerDetailsRequest) {
        CustomerDetails customerDetails = new CustomerDetails(customerDetailsRequest.getFirstName(),
                customerDetailsRequest.getLastName(),
                customerDetailsRequest.getEmail());
        return customerDetails;
    }
}
