package com.example.computershop.repository;

import com.example.computershop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    @Query("SELECT p FROM Product p WHERE TYPE(p) = :productClass")
    List<Product> findByProductClass(@Param("productClass") Class<? extends Product> productClass);

}
