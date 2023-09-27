package com.example.jamesie_be.service;

import com.example.jamesie_be.model.Accounts;

import java.util.List;

public interface IAccountService {
    List<Accounts> findAll();

    void save(Accounts accounts);
}
