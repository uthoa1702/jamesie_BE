package com.example.jamesie_be.repository;

import com.example.jamesie_be.model.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProductTypeRepository extends JpaRepository<ProductType,Long> {
}
