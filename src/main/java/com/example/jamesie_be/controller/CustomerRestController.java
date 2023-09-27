package com.example.jamesie_be.controller;


import com.example.jamesie_be.model.Accounts;
import com.example.jamesie_be.model.Customers;
import com.example.jamesie_be.model.Role;
import com.example.jamesie_be.service.IAccountService;
import com.example.jamesie_be.service.ICustomerService;
import com.example.jamesie_be.service.impl.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Random;

@RestController
@CrossOrigin("*")
@RequestMapping("/customer")
public class CustomerRestController {
    @Autowired
    private ICustomerService iCustomerService;

    @Autowired
    private IAccountService iAccountService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    private static UserDetails getUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (UserDetails) authentication.getPrincipal();
    }

    @GetMapping("/information")
    @PreAuthorize("hasAnyRole('ROLE_CUSTOMER', 'ROLE_ADMIN')")
    public ResponseEntity<?> getInformation() {
        Customers customers = iCustomerService.findByUsername(getUserDetails().getUsername());
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    @PostMapping("/Verification")
    @Transactional
    public ResponseEntity<?> verification(@RequestParam("confirmationCode") Integer confirmationCode) {
        List<Accounts> accountsList = iAccountService.findAll();
        for (int i = 0; i < accountsList.size(); i++) {
            if (Objects.equals(confirmationCode, accountsList.get(i).getConfirmationCode())) {
                accountsList.get(i).setEnable(true);
                accountsList.get(i).setConfirmationCode(null);
                return new ResponseEntity<>("Your account is verified", HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("Wrong code",HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/createCustomer")
    @Transactional
    public ResponseEntity<?> createCustomer(@RequestBody Customers customers,
                                            @RequestParam("confirmPassword") String confirmPassword,
                                            @RequestParam("password") String password) {
        if (!Objects.equals(password, confirmPassword)) {
            return new ResponseEntity<>("Check your confirmation password", HttpStatus.BAD_REQUEST);
        }

        String encodePass = passwordEncoder.encode(customers.getAccounts().getPassword());
        customers.getAccounts().setPassword(encodePass);

        List<Accounts> accountsList = iAccountService.findAll();
        for (int i = 0; i < accountsList.size(); i++) {
            if (Objects.equals(accountsList.get(i).getName(), customers.getAccounts().getName())) {
                return new ResponseEntity<>("Your username is already used", HttpStatus.BAD_REQUEST);
            }
        }
        List<Customers> customersList = iCustomerService.findAll();
        for (int i = 0; i < customersList.size(); i++) {
            if (Objects.equals(customersList.get(i).getMail(), customers.getMail())) {
                return new ResponseEntity<>("Your email is already used", HttpStatus.BAD_REQUEST);
            }
            if (Objects.equals(customersList.get(i).getPhone(), customers.getPhone())){
                return new ResponseEntity<>("Your phone number is already used", HttpStatus.BAD_REQUEST);
            }
        }

        Random random = new Random();
        long min = 10000000; // Số nhỏ nhất có 8 chữ số
        long max = 99999999; // Số lớn nhất có 8 chữ số
        boolean flag;
        long confirmationCode;
        do {
            flag = true;
            confirmationCode = random.nextLong() % (max - min + 1) + min;

            for (int i = 0; i < accountsList.size(); i++) {
                if (Objects.equals(accountsList.get(i).getConfirmationCode(), confirmationCode)) {
                    flag = false;
                }
            }
        } while (!flag || confirmationCode <= 0);
        customers.getAccounts().setConfirmationCode((int) confirmationCode);
        Role role = iCustomerService.findRoleCustomer();
        customers.getAccounts().setRole(role);
        iAccountService.save(customers.getAccounts());
        iCustomerService.save(customers);
        String name = customers.getName();

        String to = customers.getMail();
        String subject = "Confimation code - Jamesie Store";
        String body = "Hi " + name + ",\n" +
                "\n" +
                "Your confirmation code is " + confirmationCode +
                "\n" +
                ".\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "---------------------------------------" + "\n" +
                "Name :Jamesie Store\n" +
                "Mobile : 0782391943\n" +
                "Email : uthoa1702@gmail.com\n" +
                "Address :\u200B2\u200B80\u200B \u200BTrần Hưng Đạo\u200B streets, \u200BSơn Trà\u200B District, Da Nang";

        emailService.sendMail(to, subject, body);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
