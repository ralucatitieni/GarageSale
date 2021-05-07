package com.portfolio.exception;

public class OrderAlreadyPaidException extends Exception {

    public OrderAlreadyPaidException() {
    }

    public OrderAlreadyPaidException(String message) {
        super(message);
    }
}
