package com.portfolio.service;

import com.portfolio.exception.InvalidCardException;
import org.springframework.stereotype.Component;

import java.time.YearMonth;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class CardValidator {

    private static final String cardNumberPatter = "(?:\\d{16})";
    private static final String cardCVVPatter = "(?:\\d{3})";

    public void validateCard(String cardNumber, String cardCvv, YearMonth expireDate) throws InvalidCardException {

        Pattern patternCardNumber = Pattern.compile(cardNumberPatter);
        Pattern patternCVV = Pattern.compile(cardCVVPatter);
        Matcher matcherCardNumber = patternCardNumber.matcher(cardNumber);
        Matcher matcherCVV = patternCVV.matcher(cardCvv);

        if (!matcherCardNumber.matches()) {
            throw new InvalidCardException("Card number must contains only digits");
        }
        if (cardNumber.length() != 16) {
            throw new InvalidCardException("Card number must contain 16 digits");
        }
        if (!isCardNumberValid(cardNumber)) {
            throw new InvalidCardException("Card number is not ok");
        }
        if (cardCvv.length() != 3) {
            throw new InvalidCardException("CVV not valid");
        }
        if (!matcherCVV.matches()) {
            throw new InvalidCardException("CVV must contain only digits");
        }
        if (expireDate.isBefore(YearMonth.now())) {
            throw new InvalidCardException("Card has expired");
        }
    }

    public static boolean isCardNumberValid(String cardNumber) {
        long number = Long.parseLong(cardNumber);
        int digits = 0;
        long copyOfNumber = number;
        while (copyOfNumber > 0) {
            copyOfNumber = copyOfNumber / 10;
            digits++;
        }
        long numberForDoubled = number / 10;
        int checkDigit = (int) (number % 10);
        int sumOfDubled = 0;
        for (int i = 1; i < digits; i = i + 2) {
            int digit = (int) ((numberForDoubled % 10) * 2);

            if (digit > 9) {
                int sumOfDigits = 0;
                for (int j = 0; j < 2; j++) {
                    sumOfDigits += digit % 10;
                    digit = digit / 10;
                }
                sumOfDubled += sumOfDigits;
            } else {
                sumOfDubled = (int) (sumOfDubled + (numberForDoubled % 10) * 2);
            }

            numberForDoubled = numberForDoubled / 100;
        }
        int sumOfSimple = 0;
        long numberForSimple = number / 100;
        for (int i = 0; i < digits; i = i + 2) {
            sumOfSimple = (int) (sumOfSimple + (numberForSimple % 10));
            numberForSimple = numberForSimple / 100;
        }
        int totalSum = sumOfDubled + sumOfSimple + checkDigit;

        return totalSum % 10 == 0;
    }
}