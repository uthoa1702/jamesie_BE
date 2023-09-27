package com.example.jamesie_be.service.impl;

import com.example.jamesie_be.model.Customers;
import com.example.jamesie_be.model.Orders;
import com.example.jamesie_be.repository.IOrderRepository;
import com.example.jamesie_be.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService implements IOrderService {
    @Autowired
    private IOrderRepository iOrderRepository;

    @Override
    public void save(Orders orders) {
        iOrderRepository.save(orders);
    }

    @Override
    public List<Orders> getHistoryByCustomer(Customers customers) {
        return iOrderRepository.findAllByCustomersOrderByCreateTimeDesc(customers);
    }

    @Override
    public List<Orders> getAll() {
        return iOrderRepository.findAll();
    }
}
