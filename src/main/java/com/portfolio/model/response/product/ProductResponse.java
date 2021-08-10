package com.portfolio.model.response.product;

import com.portfolio.model.purchase.Price;
import com.portfolio.model.response.Response;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@SuperBuilder
public class ProductResponse extends Response {

    public String category;
    public Price price;
    public List<String> issues;
    public Map<String, String> characteristics;
    public int stock;

}
