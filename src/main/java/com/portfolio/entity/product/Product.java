package com.portfolio.entity.product;

import com.portfolio.entity.order.Order;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Getter
@Setter
@Entity(name = "product")
@Table(name = "product")
public class Product implements Serializable {

    @ManyToMany(mappedBy = "products")
    Set<Order> orders = new HashSet<>();
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String category;
    private String currency;
    private double amount;
    private String issue;
    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private Stock stock;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductDetail> details = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        if (details.size() != product.getDetails().size()) {
            return false;
        }
        return Objects.equals(category, product.category) && Objects.equals(issue, product.issue)
                && details.containsAll(product.getDetails());
    }

    @Override
    public int hashCode() {
        return Objects.hash(category, issue, details);
    }
}