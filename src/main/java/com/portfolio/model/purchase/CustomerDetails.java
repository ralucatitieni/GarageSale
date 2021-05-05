package com.portfolio.model.purchase;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerDetails {

    private String firstName;
    private String lastName;
    private String email;

    public CustomerDetails(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
}