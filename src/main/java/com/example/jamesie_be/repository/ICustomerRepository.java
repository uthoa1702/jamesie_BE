package com.example.jamesie_be.repository;

import com.example.jamesie_be.model.Customers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ICustomerRepository extends JpaRepository<Customers, Long> {
    @Query(value = "SELECT c.*\n" +
            "FROM customers AS c\n" +
            "         INNER JOIN accounts A ON c.account_id = A.id\n" +
            "\n" +
            "WHERE is_delete = FALSE\n" +
            "  AND A.name = :username\n" +
            "  AND A.role_id = 2",nativeQuery = true)
    Customers findByUsername(@Param("username") String username);
}
