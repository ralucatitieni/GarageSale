package com.portfolio.exception;

public class InvalidOrderRequestException extends Exception {

    public InvalidOrderRequestException() {
    }

    public InvalidOrderRequestException(String message) {
        super(message);
    }

    public InvalidOrderRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
