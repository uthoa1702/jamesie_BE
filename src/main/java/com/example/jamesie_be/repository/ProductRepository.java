package com.example.jamesie_be.repository;

import com.example.jamesie_be.model.DTO.IProductDTO;
import com.example.jamesie_be.model.Products;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Products, Long> {

    @Query(value = "SELECT DISTINCT PC.name       AS color,\n" +
            "                p.name        AS name,\n" +
            "                p.price       AS price,\n" +
            "                p.description AS description,\n" +
            "                p.image1      AS image1,\n" +
            "                p.image2      AS image2,\n" +
            "                p.image3      AS image3,\n" +
            "                I.url         AS url \n" +
            "FROM products p\n" +
            "         INNER JOIN product_color PC ON p.product_color_id = PC.id\n" +
            "         INNER JOIN product_type PT ON p.product_type_id = PT.id\n" +
            "INNER JOIN images I ON p.id = I.product_id\n" +
            "WHERE p.is_delete = FALSE\n" +
            "  AND p.amount > 0\n" +
            "  AND price BETWEEN :from AND :to\n" +
            "  AND PC.name LIKE :color \n" +
            "  AND PT.name LIKE :type\n" +
            "  AND p.name LIKE :productName\n" +
            "AND I.id IN (SELECT MIN(images.id) AS image1\n" +
            "               FROM images\n" +
            "               GROUP BY images.product_id)",
            countQuery = "SELECT COUNT(*) FROM (" +
                    "SELECT DISTINCT PC.name       AS color,\n" +
                    "                p.name        AS name,\n" +
                    "                p.price       AS price,\n" +
                    "                p.description AS description,\n" +
                    "                p.image1      AS image1,\n" +
                    "                p.image2      AS image2,\n" +
                    "                p.image3      AS image3,\n" +
                    "                I.url         AS url \n" +
                    "FROM products p\n" +
                    "         INNER JOIN product_color PC ON p.product_color_id = PC.id\n" +
                    "         INNER JOIN product_type PT ON p.product_type_id = PT.id\n" +
                    "INNER JOIN images I ON p.id = I.product_id\n" +
                    "WHERE p.is_delete = FALSE\n" +
                    "  AND price BETWEEN :from AND :to\n" +
                    "  AND PC.name LIKE :color \n" +
                    "  AND PT.name LIKE :type\n" +
                    "  AND p.name LIKE :productName\n" +
                    "AND I.id IN (SELECT MIN(images.id) AS image1\n" +
                    "               FROM images\n" +
                    "               GROUP BY images.product_id)" +
                    " ) as source",
            nativeQuery = true)
    Page<IProductDTO> getProducts(Pageable pageable,

                                  @Param("from") double from,
                                  @Param("to") double to,
                                  @Param("color") String color,
                                  @Param("type") String type,
                                  @Param("productName") String productName);


    @Query(value = "SELECT MAX(price)\n" +
            "FROM products\n" +
            "WHERE is_delete = FALSE\n" +
            "  AND amount > 0", nativeQuery = true)
    Double getMaxPrice();

    Products findByName(String name);

    @Query(value = "SELECT *\n" +
            "FROM products AS p\n" +
            "WHERE name = :name\n" +
            "  AND product_size_id = :id\n" +
            "  AND is_delete = FALSE", nativeQuery = true)
    Products findByNameAndProductSize(@Param("name") String name, @Param("id") Long id);

}
