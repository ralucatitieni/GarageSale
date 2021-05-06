package com.portfolio.controller;


import com.portfolio.model.request.order.CardDetailsRequest;
import com.portfolio.model.request.order.OrderRequest;
import com.portfolio.model.response.order.OrderResponse;
import com.portfolio.model.response.order.PaymentResponse;
import com.portfolio.service.OrderServiceImpl;
import com.portfolio.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {


    private OrderServiceImpl orderService;
    private PaymentService paymentService;

    @Autowired
    public OrderController(OrderServiceImpl orderService, PaymentService paymentService) {
        this.orderService = orderService;
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<Object> placeOrder(@RequestBody OrderRequest orderRequest) {
        OrderResponse orderResponse = orderService.placeOrder(orderRequest);
        return new ResponseEntity<>(orderResponse, HttpStatus.CREATED);
    }

    @PostMapping("/pay/{orderId}")
    public ResponseEntity<Object> payOrder(@PathVariable("orderId") String orderId, @RequestBody CardDetailsRequest cardDetailsRequest) {
        PaymentResponse paymentResponse = paymentService.payOrder(orderId, cardDetailsRequest);
        return new ResponseEntity<>(paymentResponse, HttpStatus.OK);
    }
}
