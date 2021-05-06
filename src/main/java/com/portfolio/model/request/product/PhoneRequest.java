package com.portfolio.model.request.product;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
public class PhoneRequest {


    @NotNull(message = "Brand is mandatory")
    private String brand;
    @NotNull(message = "Model is mandatory")
    private String model;
    @NotNull(message = "Operating system is mandatory")
    private String operatingSystem;
    @NotNull(message = "Memory size is mandatory")
    private Integer memorySize;
    @NotNull(message = "Operating System is mandatory")
    private Boolean dualSim;
}
