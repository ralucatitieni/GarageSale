package com.portfolio.model.response.product;

import com.portfolio.model.purchase.Price;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
public class ProductResponse {

    public int productId;
    public String category;
    public Price price;
    public List<String> issues;
    public Map<String, String> characteristics;
    public int stock;

}
