package com.example.jamesie_be.model;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;

@EntityListeners(AuditingEntityListener.class)
@SQLDelete(sql = "UPDATE customers SET is_delete = 1 WHERE id = ?")
@Where(clause = "is_delete = 0")
@Entity
public class Customers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String phone;

    @Column(nullable = false, unique = true)
    private String mail;


    @Column(columnDefinition = "TEXT")
    private String address;


    private String country;

    private LocalDate birthday;

    @OneToOne
    @JoinColumn(name = "account_id")
    private Accounts accounts;

    @Column(name = "is_delete", columnDefinition = "BIT DEFAULT 0")
    private boolean isDelete;

    public Customers() {

    }


    public boolean isDelete() {
        return isDelete;
    }

    public void setDelete(boolean delete) {
        isDelete = delete;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public Customers(String name, String phone, String mail, String address, String country, LocalDate birthday, Accounts accounts) {

        this.name = name;
        this.phone = phone;
        this.mail = mail;
        this.address = address;
        this.country = country;
        this.birthday = birthday;
        this.accounts = accounts;
    }

    public Customers(Long id, String name, String phone, String mail, String address, String country, LocalDate birthday, Accounts accounts, boolean isDelete) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.mail = mail;
        this.address = address;
        this.country = country;
        this.birthday = birthday;
        this.accounts = accounts;
        this.isDelete = isDelete;
    }

    public Customers(Long id) {
        this.id = id;
    }

    public Customers(String name, String phone, String mail, String address, String country) {
        this.name = name;
        this.phone = phone;
        this.mail = mail;
        this.address = address;
        this.country = country;
    }

    public Customers(Long id, String name, String phone, String mail, String address, String country, Accounts accounts, boolean isDelete) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.mail = mail;
        this.address = address;
        this.country = country;
        this.accounts = accounts;
        this.isDelete = isDelete;
    }

    public Customers(Long id, String name, String phone, String mail, String address, String country, Accounts accounts) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.mail = mail;
        this.address = address;
        this.country = country;
        this.accounts = accounts;
    }

    public Customers(Accounts accounts) {
        this.accounts = accounts;
    }

    public Customers(String name, String phone, String mail, String address, String country, Accounts accounts) {
        this.name = name;
        this.phone = phone;
        this.mail = mail;
        this.address = address;
        this.country = country;
        this.accounts = accounts;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Accounts getAccounts() {
        return accounts;
    }

    public void setAccounts(Accounts accounts) {
        this.accounts = accounts;
    }
}
