package com.ecommerce.gadgetzone.repository;

import com.ecommerce.gadgetzone.entity.Product;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface ProductRepository extends JpaRepository<Product,Integer>{

    Optional<Product> findByProductName(String productName);
}
