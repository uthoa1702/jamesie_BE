package com.example.jamesie_be.service;

import com.example.jamesie_be.model.Customers;
import com.example.jamesie_be.model.Role;

import java.util.List;

public interface ICustomerService {
    Customers findByUsername(String username);

    void save(Customers customers);

    List<Customers> findAll();

    Role findRoleCustomer();

}
