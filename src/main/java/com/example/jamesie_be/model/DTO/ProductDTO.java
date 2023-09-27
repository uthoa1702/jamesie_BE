package com.example.jamesie_be.model.DTO;


import java.util.List;

public class ProductDTO {
    private String color;

    private String name;


    private Double price;


    private Long amount;


    private String image1;


    private String image2;


    private String image3;

    private String url;


    private String description;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }

    public String getImage3() {
        return image3;
    }

    public void setImage3(String image3) {
        this.image3 = image3;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ProductDTO(String id) {
        this.color = id;
    }

    public ProductDTO() {
    }

    public ProductDTO(String name, Double price, Long amount, String image1, String image2, String image3, String description) {
        this.name = name;
        this.price = price;
        this.amount = amount;
        this.image1 = image1;
        this.image2 = image2;
        this.image3 = image3;
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ProductDTO(String color, String name, Double price, Long amount, String image1, String image2, String image3, String url, String description) {
        this.color = color;
        this.name = name;
        this.price = price;
        this.amount = amount;
        this.image1 = image1;
        this.image2 = image2;
        this.image3 = image3;
        this.url = url;
        this.description = description;
    }

    public ProductDTO(String id, String name, Double price, Long amount, String image1, String image2, String image3, String description) {
        this.color = id;
        this.name = name;
        this.price = price;
        this.amount = amount;
        this.image1 = image1;
        this.image2 = image2;
        this.image3 = image3;
        this.description = description;
    }
}
