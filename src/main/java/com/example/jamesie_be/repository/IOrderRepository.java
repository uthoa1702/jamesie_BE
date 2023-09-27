package com.example.jamesie_be.repository;

import com.example.jamesie_be.model.Customers;
import com.example.jamesie_be.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IOrderRepository extends JpaRepository<Orders,Long> {


    List<Orders> findAllByCustomersOrderByCreateTimeDesc(Customers customers);
}
