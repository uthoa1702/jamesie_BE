package com.example.jamesie_be.model;
import javax.persistence.*;

@Entity
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customers customers;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Products products;

    @Column(nullable = false)
    private Integer amount;

    public ShoppingCart() {
    }

    public ShoppingCart(Long id) {
        this.id = id;
    }

    public ShoppingCart(Customers customers, Products products, Integer amount) {
        this.customers = customers;
        this.products = products;
        this.amount = amount;
    }

    public ShoppingCart(Products products, Integer amount) {
        this.products = products;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customers getCustomers() {
        return customers;
    }

    public void setCustomers(Customers customers) {
        this.customers = customers;
    }

    public Products getProducts() {
        return products;
    }

    public void setProducts(Products products) {
        this.products = products;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
