package com.example.jamesie_be.service.impl;

import com.example.jamesie_be.model.Accounts;
import com.example.jamesie_be.repository.AccountRepository;
import com.example.jamesie_be.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService implements IAccountService {
    @Autowired private AccountRepository accountRepository;
    @Override
    public List<Accounts> findAll() {
        return accountRepository.findAll();
    }

    @Override
    public void save(Accounts accounts) {
        accountRepository.save(accounts);
    }
}
