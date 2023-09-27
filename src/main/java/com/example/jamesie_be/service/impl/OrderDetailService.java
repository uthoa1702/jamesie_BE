package com.example.jamesie_be.service.impl;

import com.example.jamesie_be.model.Customers;
import com.example.jamesie_be.model.OrderDetail;
import com.example.jamesie_be.repository.IOrderDetailRepository;
import com.example.jamesie_be.service.IOrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailService implements IOrderDetailService {
    @Autowired
    private IOrderDetailRepository iOrderDetailRepository;

    @Override
    public void save(OrderDetail orderDetail) {
        iOrderDetailRepository.save(orderDetail);
    }

    @Override
    public List<OrderDetail> findByIdOrder(Long orderId) {
        return iOrderDetailRepository.findOrderDetail(orderId);
    }


}
