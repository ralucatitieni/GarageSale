package com.portfolio.model.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ErrorMessage {

    private final LocalDateTime timestamp = LocalDateTime.now();
    private HttpStatus status;
    private String message;

}

