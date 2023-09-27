package com.example.jamesie_be.repository;

import com.example.jamesie_be.model.Accounts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Accounts, Long> {
    Accounts findAccountsByNameAndEnableIsTrue(String name);
}
