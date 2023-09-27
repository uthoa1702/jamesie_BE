package com.example.jamesie_be.service;

import com.example.jamesie_be.model.Customers;
import com.example.jamesie_be.model.OrderDetail;

import java.util.List;

public interface IOrderDetailService {
    void save(OrderDetail orderDetail);



    List<OrderDetail> findByIdOrder(Long orderId);
}
