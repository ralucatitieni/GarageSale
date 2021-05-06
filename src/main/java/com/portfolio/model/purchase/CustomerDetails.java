package com.portfolio.model.purchase;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CustomerDetails {

    private String firstName;
    private String lastName;
    private String email;

    @Override
    public String toString() {
        return "First Name:" + firstName + '\'' +
                ", Last Name: " + lastName + '\'' +
                ", Email: " + email;
    }
}