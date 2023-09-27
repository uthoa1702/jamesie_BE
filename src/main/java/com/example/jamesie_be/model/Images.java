package com.example.jamesie_be.model;


import javax.persistence.*;

@Entity
public class Images {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, columnDefinition = "TEXT")
    private String url;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Products products;

    public Images() {
    }

    public Images(Long id) {
        this.id = id;
    }

    public Images(Long id, String url, Products products) {
        this.id = id;
        this.url = url;
        this.products = products;
    }

    public Images(String url, Products products) {
        this.url = url;
        this.products = products;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Products getProducts() {
        return products;
    }

    public void setProducts(Products products) {
        this.products = products;
    }
}
