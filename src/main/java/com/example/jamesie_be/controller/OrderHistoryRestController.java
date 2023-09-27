package com.example.jamesie_be.controller;

import com.example.jamesie_be.model.Customers;
import com.example.jamesie_be.model.OrderDetail;
import com.example.jamesie_be.model.Orders;
import com.example.jamesie_be.service.ICustomerService;
import com.example.jamesie_be.service.IOrderDetailService;
import com.example.jamesie_be.service.IOrderService;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/history")
public class OrderHistoryRestController {
    @Autowired
    private IOrderService iOrderService;

    @Autowired
    private IOrderDetailService iOrderDetailService;

    @Autowired
    private ICustomerService iCustomerService;

    private static UserDetails getUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (UserDetails) authentication.getPrincipal();
    }

    @GetMapping("/getHistoryList")
    @PreAuthorize("hasAnyRole('ROLE_CUSTOMER', 'ROLE_ADMIN')")
    public ResponseEntity<?> getlist() {
        Customers customers = iCustomerService.findByUsername(getUserDetails().getUsername());
        List<Orders> ordersList =  iOrderService.getHistoryByCustomer(customers);
        return new ResponseEntity<>(ordersList, HttpStatus.OK);
    }

    @GetMapping("/orderDetail")
    @PreAuthorize("hasAnyRole('ROLE_CUSTOMER', 'ROLE_ADMIN')")
    public ResponseEntity<?> getDetail(@RequestParam("orderId")Long orderId){

        List<OrderDetail>orderDetailList = iOrderDetailService.findByIdOrder(orderId);
        return new ResponseEntity<>(orderDetailList, HttpStatus.OK);
    }
}
