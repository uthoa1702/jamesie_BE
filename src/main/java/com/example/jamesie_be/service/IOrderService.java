package com.example.jamesie_be.service;

import com.example.jamesie_be.model.Customers;
import com.example.jamesie_be.model.Orders;

import java.util.List;

public interface IOrderService {
    void save(Orders orders);

    List<Orders> getHistoryByCustomer(Customers customers);

    List<Orders> getAll();
}
