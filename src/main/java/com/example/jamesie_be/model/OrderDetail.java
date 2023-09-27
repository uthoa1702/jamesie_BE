package com.example.jamesie_be.model;


import javax.persistence.*;

@Entity
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Orders orders;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Products products;

    private Integer amount;

    private Double total;
    private String status;

    public OrderDetail(Orders orders, Products products, Integer amount, Double total, String status) {
        this.orders = orders;
        this.products = products;
        this.amount = amount;
        this.total = total;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public OrderDetail() {
    }

    public OrderDetail(Long id) {
        this.id = id;
    }

    public OrderDetail(Long id, Orders orders, Products products, Integer amount, Double total) {
        this.id = id;
        this.orders = orders;
        this.products = products;
        this.amount = amount;
        this.total = total;
    }

    public OrderDetail(Orders orders, Products products) {
        this.orders = orders;
        this.products = products;
    }

    public OrderDetail(Orders orders, Products products, Integer amount, Double total) {
        this.orders = orders;
        this.products = products;
        this.amount = amount;
        this.total = total;
    }

    public OrderDetail(Orders orders, Products products, Integer amount) {
        this.orders = orders;
        this.products = products;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Orders getOrders() {
        return orders;
    }

    public void setOrders(Orders orders) {
        this.orders = orders;
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

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}
