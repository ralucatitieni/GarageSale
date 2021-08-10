package com.portfolio.configuration;
import com.portfolio.validator.CardValidator;
import com.portfolio.validator.OrderRequestValidator;
import com.portfolio.validator.ProductRequestValidator;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public EmailValidator emailValidator() {
        EmailValidator emailValidator = EmailValidator.getInstance();
        return emailValidator;
    }

    @Bean
    public CardValidator cardValidator() {
        CardValidator cardValidator = CardValidator.getInstance();
        return cardValidator;
    }
}