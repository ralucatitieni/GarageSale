package com.portfolio.model.request.product;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
public class KeyboardRequest {

    @NotNull(message = "Brand is mandatory")
    private String brand;
    @NotNull(message = "Type is mandatory")
    private String type;
}