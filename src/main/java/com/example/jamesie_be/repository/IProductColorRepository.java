package com.example.jamesie_be.repository;

import com.example.jamesie_be.model.ProductColor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductColorRepository extends JpaRepository<ProductColor, Long> {
}
