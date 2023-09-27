package com.example.jamesie_be.service.impl;

import com.example.jamesie_be.model.Customers;
import com.example.jamesie_be.model.Role;
import com.example.jamesie_be.repository.ICustomerRepository;
import com.example.jamesie_be.repository.IRoleRepository;
import com.example.jamesie_be.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService implements ICustomerService {
    @Autowired
    private ICustomerRepository iCustomerRepository;

    @Autowired private IRoleRepository iRoleRepository;

    @Override
    public Customers findByUsername(String username) {

        return iCustomerRepository.findByUsername(username);
    }

    @Override
    public void save(Customers customers) {
        iCustomerRepository.save(customers);
    }

    @Override
    public List<Customers> findAll() {
        return iCustomerRepository.findAll();
    }

    @Override
    public Role findRoleCustomer() {
        return iRoleRepository.findById(2L).get();
    }
}
