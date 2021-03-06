package com.portfolio.model.request.product;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;


@Getter
@Setter
public class ProductRequest {

    @NotNull(message = "Category is mandatory")
    private String category;
    @NotNull(message = "Currency is mandatory")
    private String currency;
    @NotNull(message = "Amount is mandatory")
    private Double amount;
    @NotNull(message = "Issue is mandatory")
    private List<String> issues;
    @NotNull
    private Integer stock;

    private LaptopRequest laptopRequest;
    private PhoneRequest phoneRequest;
    private HeadphoneRequest headphoneRequest;
    private KeyboardRequest keyboardRequest;
}
