package com.portfolio.entity.order;

import com.portfolio.entity.product.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Entity(name = "order")
@Table(name = "orders")
@NoArgsConstructor
public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private boolean paid;
    private double total;
    private LocalDate orderDate;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "order_product", joinColumns = @JoinColumn(name = "order_id"), inverseJoinColumns = @JoinColumn(name = "product_id"))
    private Set<Product> products = new HashSet<>();

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    private Order(Builder builder) {
        this.total = builder.total;
        this.products = builder.products;
        this.customer = builder.customer;
        this.orderDate = builder.orderDate;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public static class Builder {

        private double total;
        private LocalDate orderDate;
        private Set<Product> products;
        private Customer customer;

        public Builder() {
            this.orderDate = LocalDate.now();
        }

        public Builder total(double total) {
            this.total = total;
            return this;
        }

        public Builder products(Set<Product> products) {
            this.products = products;
            return this;
        }

        public Builder customer(Customer customer) {
            this.customer = customer;
            return this;
        }

        public Order build() {
            return new Order(this);
        }
    }


}