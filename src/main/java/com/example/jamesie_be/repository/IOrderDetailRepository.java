package com.example.jamesie_be.repository;

import com.example.jamesie_be.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IOrderDetailRepository extends JpaRepository<OrderDetail, Long> {


    @Query(value = "SELECT * FROM order_detail  WHERE order_id = :orderId", nativeQuery = true)
    List<OrderDetail> findOrderDetail(@Param("orderId") Long orderId);
}
