package com.example.jamesie_be.controller;

import com.example.jamesie_be.config.JwtTokenUtil;
import com.example.jamesie_be.model.*;
import com.example.jamesie_be.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Random;

@RestController
@CrossOrigin("*")
@RequestMapping("/order")
public class OrderRestController {
    @Autowired
    private IShoppingCartService iShoppingCartService;
    @Autowired
    private IProductService iProductService;

    @Autowired
    private ICustomerService iCustomerService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private IOrderService iOrderService;
    @Autowired
    private IOrderDetailService iOrderDetailService;

    private static UserDetails getUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (UserDetails) authentication.getPrincipal();
    }

    @PostMapping("/createOrder")
    @Transactional
    @PreAuthorize("hasAnyRole('ROLE_CUSTOMER', 'ROLE_ADMIN')")
    public ResponseEntity<?> createOrder() {
        List<ShoppingCart> shoppingCartList = iShoppingCartService.findByUsername(getUserDetails().getUsername());

        Customers customers = iCustomerService.findByUsername(getUserDetails().getUsername());
        Orders orders = new Orders(customers);
        iOrderService.save(orders);
        int count = 0;
        double sum = 0;
        for (int i = 0; i < shoppingCartList.size(); i++) {
            if (shoppingCartList.get(i).getProducts().getAmount() >= shoppingCartList.get(i).getAmount()) {
                Long change = shoppingCartList.get(i).getProducts().getAmount() - shoppingCartList.get(i).getAmount();
                OrderDetail orderDetail = new OrderDetail(
                        orders,
                        shoppingCartList.get(i).getProducts(),
                        shoppingCartList.get(i).getAmount(),
                        (shoppingCartList.get(i).getProducts().getPrice() * shoppingCartList.get(i).getAmount()),
                        "Confirmed");
                orderDetail.getProducts().setAmount(change);
                iOrderDetailService.save(orderDetail);
                iShoppingCartService.deleteProductInCart(getUserDetails().getUsername(), orderDetail.getProducts().getId());
                sum = sum + orderDetail.getTotal();

            } else {

                OrderDetail orderDetail = new OrderDetail(
                        orders,
                        shoppingCartList.get(i).getProducts(),
                        shoppingCartList.get(i).getAmount(),
                        (shoppingCartList.get(i).getProducts().getPrice() * shoppingCartList.get(i).getAmount()),
                        "Pending");
                iOrderDetailService.save(orderDetail);
                iShoppingCartService.deleteProductInCart(getUserDetails().getUsername(), orderDetail.getProducts().getId());
                count++;
                sum = sum + orderDetail.getTotal();
            }

        }
        if (count > 0) {
            orders.setStatus("Pending");
        } else {
            orders.setStatus("Confirmed");
        }
        orders.setTotal(sum);
        List<Orders> ordersList = iOrderService.getAll();
        long code;
        Random random = new Random();
        long min = 10000000; // Số nhỏ nhất có 8 chữ số
        long max = 99999999; // Số lớn nhất có 8 chữ số
        boolean flag;
        String orderCode;
        do {
            flag = true;
            code = random.nextLong() % (max - min + 1) + min;
             orderCode = "OD-" + code;
            for (int i = 0; i < ordersList.size(); i++) {
                if (Objects.equals(ordersList.get(i).getOrderCode(), orderCode)) {
                    flag = false;
                }
            }
        } while (!flag || code <= 0);
        orders.setOrderCode(orderCode);
        iOrderService.save(orders);
        return new ResponseEntity<>("Successfully", HttpStatus.OK);
    }
}
