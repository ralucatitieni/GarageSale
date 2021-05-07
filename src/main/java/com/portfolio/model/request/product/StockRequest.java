package com.portfolio.model.request.product;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class StockRequest {

    @NotNull
    private Integer stock;

}
