package com.portfolio.configuration;

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
}