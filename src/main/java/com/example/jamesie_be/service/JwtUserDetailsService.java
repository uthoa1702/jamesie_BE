package com.example.jamesie_be.service;

import com.example.jamesie_be.model.Accounts;
import com.example.jamesie_be.repository.AccountRepository;
import org.hibernate.validator.internal.util.privilegedactions.GetAnnotationAttribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class JwtUserDetailsService implements UserDetailsService {
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      Accounts accounts = accountRepository.findAccountsByNameAndEnableIsTrue(username);
        Collection<GrantedAuthority> roleList = new ArrayList<>();
        roleList.add(new SimpleGrantedAuthority(accounts.getRole().getName()));




        if (accounts.getName().equals(username)) {
            return new User(username, accounts.getPassword(), roleList);
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }
}