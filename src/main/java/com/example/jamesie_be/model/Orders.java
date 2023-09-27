package com.example.jamesie_be.model;


import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@SQLDelete(sql = "UPDATE orders SET is_delete = 1 WHERE id = ?")

@Where(clause = "is_delete = 0")
@Entity
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @CreationTimestamp
    @Column(name = "create_time", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT now()")
    private LocalDateTime createTime;
    @UpdateTimestamp
    @Column(name = "update_time", nullable = false, columnDefinition = "TIMESTAMP DEFAULT now()")
    private LocalDateTime updateTime;

    @Column(name = "is_delete", columnDefinition = "BIT DEFAULT 0")
    private boolean isDelete;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customers customers;

    private String status;
    private Double total;
    private String orderCode;

    public Orders(Customers customers, String status, Double total, String orderCode) {
        this.customers = customers;
        this.status = status;
        this.total = total;
        this.orderCode = orderCode;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public Orders(Long id, LocalDateTime createTime, LocalDateTime updateTime, boolean isDelete, Customers customers, String status, Double total, String orderCode) {
        this.id = id;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.isDelete = isDelete;
        this.customers = customers;
        this.status = status;
        this.total = total;
        this.orderCode = orderCode;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Orders(Customers customers, String status, Double total) {
        this.customers = customers;
        this.status = status;
        this.total = total;
    }

    public Orders(Long id, LocalDateTime createTime, LocalDateTime updateTime, boolean isDelete, Customers customers, String status, Double total) {
        this.id = id;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.isDelete = isDelete;
        this.customers = customers;
        this.status = status;
        this.total = total;
    }

    public Orders(String status) {
        this.status = status;
    }

    public Orders(Customers customers, String status) {
        this.customers = customers;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Orders(Long id, LocalDateTime createTime, LocalDateTime updateTime, boolean isDelete, Customers customers, String status) {
        this.id = id;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.isDelete = isDelete;
        this.customers = customers;
        this.status = status;
    }

    public Orders() {
    }

    public Orders(Long id) {
        this.id = id;
    }

    public Orders(Long id, LocalDateTime createTime, LocalDateTime updateTime, boolean isDelete, Customers customers) {
        this.id = id;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.isDelete = isDelete;
        this.customers = customers;
    }

    public Orders(Customers customers) {
        this.customers = customers;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Customers getCustomers() {
        return customers;
    }

    public void setCustomers(Customers customers) {
        this.customers = customers;
    }
}
