package com.portfolio.model.request.order;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Getter
@AllArgsConstructor
public class OrderRequest {

    @NotNull
    private CustomerDetailsRequest customerDetailsRequest;
    @NotNull
    private Set<String> productIds;

}
