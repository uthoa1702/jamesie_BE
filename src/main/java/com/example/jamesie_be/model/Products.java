package com.example.jamesie_be.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;


@EntityListeners(AuditingEntityListener.class)
@SQLDelete(sql = "UPDATE products SET is_delete = 1 WHERE id = ?")
@Where(clause = "is_delete = 0")
@Entity
public class Products {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private Long amount;

    @Column(columnDefinition = "TEXT")
    private String image1;

    @Column(columnDefinition = "TEXT")
    private String image2;

    @Column(columnDefinition = "TEXT")
    private String image3;

    @Column(columnDefinition = "TEXT")
    private String description;

    @CreationTimestamp
    @Column(name = "create_time", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT now()")
    private LocalDateTime createTime;
    @UpdateTimestamp
    @Column(name = "update_time", nullable = false, columnDefinition = "TIMESTAMP DEFAULT now()")
    private LocalDateTime updateTime;

    @Column(name = "is_delete", columnDefinition = "BIT DEFAULT 0")
    private boolean isDelete;

    @Column(columnDefinition = "BIT")
    private Boolean gender;

    @ManyToOne
    @JoinColumn(name = "product_color_id", nullable = false)
    private ProductColor productColor;


    @ManyToOne
    @JoinColumn(name = "product_size_id", nullable = false)
    private ProductSize productSize;


    @ManyToOne
    @JoinColumn(name = "product_type_id", nullable = false)
    private ProductType productType;

    public Products() {
    }

    public Products(Long id) {
        this.id = id;
    }

    public Products(String name, Double price, Long amount, String image1, String image2, String image3, String description, Boolean gender, ProductColor productColor, ProductSize productSize, ProductType productType) {
        this.name = name;
        this.price = price;
        this.amount = amount;
        this.image1 = image1;
        this.image2 = image2;
        this.image3 = image3;
        this.description = description;
        this.gender = gender;
        this.productColor = productColor;
        this.productSize = productSize;
        this.productType = productType;
    }

    public Products(Long id, String name, Double price, Long amount, String image1, String image2, String image3, String description, LocalDateTime createTime, LocalDateTime updateTime, boolean isDelete, Boolean gender, ProductColor productColor, ProductSize productSize, ProductType productType) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.amount = amount;
        this.image1 = image1;
        this.image2 = image2;
        this.image3 = image3;
        this.description = description;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.isDelete = isDelete;
        this.gender = gender;
        this.productColor = productColor;
        this.productSize = productSize;
        this.productType = productType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public boolean isDelete() {
        return isDelete;
    }

    public void setDelete(boolean delete) {
        isDelete = delete;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public ProductColor getProductColor() {
        return productColor;
    }

    public void setProductColor(ProductColor productColor) {
        this.productColor = productColor;
    }

    public ProductSize getProductSize() {
        return productSize;
    }

    public void setProductSize(ProductSize productSize) {
        this.productSize = productSize;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }
}
