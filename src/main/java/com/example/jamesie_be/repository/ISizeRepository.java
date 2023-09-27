package com.example.jamesie_be.repository;


import com.example.jamesie_be.model.ProductSize;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ISizeRepository extends JpaRepository<ProductSize, Long> {

    @Query(value = "SELECT *\n" +
            "FROM product_size\n" +
            "         INNER JOIN products P ON product_size.id = P.product_size_id\n" +
            "WHERE p.name = :productName AND p.amount > 0",nativeQuery = true)
    List<ProductSize> getSize(@Param("productName") String productName);
}
