package com.example.jamesie_be.service;

import com.example.jamesie_be.model.DTO.IImageDTO;
import com.example.jamesie_be.model.DTO.ProductDTO;
import com.example.jamesie_be.model.ProductSize;
import com.example.jamesie_be.model.ProductType;
import com.example.jamesie_be.model.Products;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IProductService {
    Page<ProductDTO> getProductsPage(Pageable pageable, String sortBy, double from, double to, String color, String type, String productName);

    Double getMaxPrice();

    List<IImageDTO> getImages(String id);


    List<ProductSize> getSize(String productName);

    List<ProductType> getType();

    Products findByName(String name);
    Products findByNameAndSize(String name, Long sizeId);

    void save(Products products);

    Products findById(Long productId);
}
