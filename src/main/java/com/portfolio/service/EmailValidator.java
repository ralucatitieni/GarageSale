package com.portfolio.service;

import com.portfolio.exception.InvalidEmailException;

public class EmailValidator {

    public static void validateEmail(String email) throws InvalidEmailException {
        if (!email.contains("@")) {
            throw new InvalidEmailException("Must contain \"@\" .");
        }
        if (email.startsWith("@") || email.endsWith("@")) {
            throw new InvalidEmailException("Email cannot start or end with \"@\" .");
        }
        if (email.contains("@.")) {
            throw new InvalidEmailException("Email must contain domain name");
        }
        if (email.startsWith(".") || email.endsWith(".")) {
            throw new InvalidEmailException("\"Email cannot start or end with \".\" .");
        }
        if (email.contains("..")) {
            throw new InvalidEmailException("Email cannot have two consecutive dots");
        }
    }
}
