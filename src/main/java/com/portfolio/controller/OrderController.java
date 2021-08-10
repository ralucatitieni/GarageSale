package com.portfolio.controller;

import com.portfolio.model.request.order.OrderRequest;
import com.portfolio.model.request.order.PaymentRequest;
import com.portfolio.model.response.order.OrderResponse;
import com.portfolio.model.response.order.PaymentResponse;
import com.portfolio.service.OrderService;
import com.portfolio.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/orders")
public class OrderController {


    private OrderService orderService;
    private PaymentService paymentService;

    @Autowired
    public OrderController(OrderService orderService, PaymentService paymentService) {
        this.orderService = orderService;
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<Object> placeOrder(@Valid @RequestBody OrderRequest orderRequest) {
        OrderResponse orderResponse = orderService.placeOrder(orderRequest);
        return new ResponseEntity<>(orderResponse, HttpStatus.CREATED);
    }

    @PostMapping("/pay")
    public ResponseEntity<Object> payOrder(@RequestParam Integer id, @Valid @RequestBody PaymentRequest paymentRequest) {
        PaymentResponse paymentResponse = paymentService.finishPurchase(id, paymentRequest);
        return new ResponseEntity<>(paymentResponse, HttpStatus.OK);
    }
}