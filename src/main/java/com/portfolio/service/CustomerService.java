package com.portfolio.service;

import com.portfolio.exception.InvalidCardException;
import com.portfolio.exception.InvalidEmailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.YearMonth;

@Component
public class CustomerService {
    private CardValidator cardValidator;
    private EmailValidator emailValidator;

    @Autowired
    public CustomerService(CardValidator cardValidator, EmailValidator emailValidator) {
        this.cardValidator = cardValidator;
        this.emailValidator = emailValidator;
    }

    public boolean checkEmail(String email) {
        try {
            emailValidator.validateEmail(email);
            return true;
        } catch (InvalidEmailException e) {
            System.out.println("Invalid email: " + e.getMessage());
        }
        return false;
    }

    public boolean checkCard(String cardNumber, String cardCvv, YearMonth expireDate) {
        try {
            cardValidator.validateCard(cardNumber, cardCvv, expireDate);
            return true;
        } catch (InvalidCardException e) {
            System.out.println("Invalid card: " + e.getMessage());
        }
        return false;
    }
}
