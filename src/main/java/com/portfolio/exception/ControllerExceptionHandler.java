package com.portfolio.exception;

import com.portfolio.model.error.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> handleException(MethodArgumentNotValidException exception) {
        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.BAD_REQUEST, exception.getMessage());
        return new ResponseEntity(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<ErrorMessage> handleException(InvalidRequestException exception) {
        ErrorMessage errorMessage;
        if (exception.getCause() instanceof ItemNotFoundException) {
            errorMessage = new ErrorMessage(HttpStatus.NOT_FOUND, exception.getMessage());
        } else {
            errorMessage = new ErrorMessage(HttpStatus.BAD_REQUEST, exception.getMessage());
        }
        return new ResponseEntity(errorMessage, errorMessage.getStatus());
    }

    @ExceptionHandler(ItemNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleException(ItemNotFoundException exception) {
        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.NOT_FOUND, exception.getMessage());
        return new ResponseEntity(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> handleException(Exception exception) {
        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.SERVICE_UNAVAILABLE, "Unexpected server error");
        return new ResponseEntity(errorMessage, HttpStatus.SERVICE_UNAVAILABLE);
    }
}