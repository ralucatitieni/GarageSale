package com.portfolio.model.response.product;

import com.portfolio.model.purchase.Price;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@Builder
public class ProductResponse {

    public String productId;
    public String productCategory;
    public Price price;
    public String issue;
    public Map<String, String> characteristics;
    public int stock;

}
