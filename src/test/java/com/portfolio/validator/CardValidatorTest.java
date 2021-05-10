package com.portfolio.validator;

import com.portfolio.exception.InvalidCardException;
import com.portfolio.model.purchase.Card;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.YearMonth;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class CardValidatorTest {

    @Mock
    private Card card;
    private CardValidator cardValidator;

    @BeforeEach
    public void setup() {
        cardValidator = CardValidator.getInstance();

        when(card.getCardNumber()).thenReturn("5299121004475672");
        when(card.getCardCvv()).thenReturn("474");
    }

    @Test
    void validateCardForWrongCardNumber() {
        when(card.getCardNumber()).thenReturn("299121004475672");
        Exception exception = assertThrows(InvalidCardException.class, () -> cardValidator.validateCard(card));
        assertEquals("Card number must contain 16 digits", exception.getMessage());
    }

    @Test
    void validateCardForLuhnAlgorithm() {
        when(card.getCardNumber()).thenReturn("5299121004475673");
        Exception exception = assertThrows(InvalidCardException.class, () -> cardValidator.validateCard(card));
        assertEquals("Card number is not valid", exception.getMessage());
    }

    @Test
    void validateCardForWrongCardCVV() {
        when(card.getCardCvv()).thenReturn("47p");
        Exception exception = assertThrows(InvalidCardException.class, () -> cardValidator.validateCard(card));
        assertEquals("CVV must contain 3 digits", exception.getMessage());
    }

    @Test
    void validateCardForWrongExpireDate() {
        when(card.getExpire()).thenReturn(YearMonth.of(2000, 8));
        Exception exception = assertThrows(InvalidCardException.class, () -> cardValidator.validateCard(card));
        assertEquals("Card has expired", exception.getMessage());
    }
}
