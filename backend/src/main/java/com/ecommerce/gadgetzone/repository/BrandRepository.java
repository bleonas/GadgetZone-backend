package com.ecommerce.gadgetzone.repository;

import com.ecommerce.gadgetzone.entity.Brand;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends JpaRepository<Brand,Integer>{
    
    Optional<Brand> findByNameBrand(String nameBrand);
}
