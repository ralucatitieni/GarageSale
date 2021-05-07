package com.portfolio.service;

import com.portfolio.entity.order.Order;
import com.portfolio.entity.product.Stock;
import com.portfolio.enums.RequestType;
import com.portfolio.exception.InvalidEmailException;
import com.portfolio.exception.InvalidOrderRequestException;
import com.portfolio.exception.InvalidRequestException;
import com.portfolio.exception.ItemNotFoundException;
import com.portfolio.model.request.order.OrderMapper;
import com.portfolio.model.request.order.OrderRequest;
import com.portfolio.model.response.ResponseFactory;
import com.portfolio.model.response.ResponseFactoryProvider;
import com.portfolio.model.response.order.OrderResponse;
import com.portfolio.repository.CustomerRepository;
import com.portfolio.repository.OrderRepository;
import com.portfolio.repository.StockRepository;
import com.portfolio.validator.OrderRequestValidator;
import com.portfolio.validator.ProductRequestValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class OrderServiceImpl implements OrderService {

    private final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
    private OrderMapper orderMapper;
    private OrderRequestValidator orderRequestValidator;
    private OrderRepository orderRepository;
    private CustomerRepository customerRepository;
    private ProductRequestValidator productRequestValidator;
    private StockRepository stockRepository;
    private ResponseFactory<Order, OrderResponse> orderResponseFactory;

    @Autowired
    public OrderServiceImpl(OrderMapper orderMapper, OrderRequestValidator orderRequestValidator,
                            OrderRepository orderRepository, CustomerRepository customerRepository, ProductRequestValidator productRequestValidator,
                            StockRepository stockRepository, ResponseFactoryProvider responseFactoryProvider) {
        this.orderMapper = orderMapper;
        this.orderRequestValidator = orderRequestValidator;
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.productRequestValidator = productRequestValidator;
        this.stockRepository = stockRepository;
        this.orderResponseFactory = responseFactoryProvider.getFactory(RequestType.ORDER);
    }

    @Override
    public OrderResponse placeOrder(OrderRequest orderRequest) {
        OrderResponse orderResponse;
        Order order = orderMapper.mapToOrder(orderRequest);
        orderRequest.getProductIds().forEach(id -> {
            try {
                productRequestValidator.validateProductByIdRequest(id);
            } catch (ItemNotFoundException e) {
                logger.error(e.getMessage());
                throw new InvalidRequestException(e.getMessage(), e);
            }
        });
        try {
            orderRequestValidator.validateProductsFromOrderRequest(order);
            orderRepository.save(order);
            updateStock(orderRequest.getProductIds());
            orderResponse = orderResponseFactory.createResponse(order);
            logger.info("Create order: " + order.getId());
        } catch (InvalidOrderRequestException e) {
            logger.error(e.getMessage());
            throw new InvalidRequestException(e.getMessage(), e);
        } catch (InvalidEmailException e) {
            logger.error(e.getMessage());
            customerRepository.delete(order.getCustomer());
            throw new InvalidRequestException(e.getMessage(), e);
        }
        return orderResponse;
    }

    private void updateStock(Set<Integer> ids) {
        for (Integer id : ids) {
            Stock stock = stockRepository.findById(id).orElseThrow(InvalidRequestException::new);
            stock.setNumberOfItems(stock.getNumberOfItems() - 1);
            stockRepository.save(stock);
        }
    }
}
