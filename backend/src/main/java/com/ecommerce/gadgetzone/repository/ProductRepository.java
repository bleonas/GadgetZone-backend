package com.ecommerce.gadgetzone.repository;

import com.ecommerce.gadgetzone.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer>{
}
