package com.example.jamesie_be.repository;

import com.example.jamesie_be.model.DTO.IImageDTO;
import com.example.jamesie_be.model.Images;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IImageRepository extends JpaRepository<Images,Long > {
    @Query(value = "SELECT DISTINCT url\n" +
            "FROM images\n" +
            "         INNER JOIN products P ON images.product_id = P.id\n" +
            "WHERE p.name = :id",nativeQuery = true)
    List<IImageDTO> getImages(@Param("id") String id);
}
