package com.example.jamesie_be.repository;

import com.example.jamesie_be.model.Customers;
import com.example.jamesie_be.model.Products;
import com.example.jamesie_be.model.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface IShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    ShoppingCart findByCustomersAndProducts(Customers customers, Products products);

    @Query(value = "SELECT sc.*\n" +
            "FROM shopping_cart AS sc\n" +
            "         INNER JOIN customers C ON sc.customer_id = C.id\n" +
            "         INNER JOIN accounts A ON C.account_id = A.id\n" +
            "WHERE A.name = :username",nativeQuery = true)
    List<ShoppingCart> findByUsername(@Param("username") String username);


    @Transactional
    @Modifying
    void deleteAllByCustomers(Customers customers);

    @Transactional
    @Modifying
    void deleteAllByCustomersAndProducts(Customers customers, Products products);
}
