package com.portfolio.model.request.order;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
public class CardDetailsRequest {

    @NotNull
    private String cardNumber;
    @NotNull
    private String cardCVV;
    @NotNull
    private Integer month;
    @NotNull
    private Integer year;
}
