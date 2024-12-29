package com.ecommerce.gadgetzone.repository;

import com.ecommerce.gadgetzone.entity.Warehouse;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse,Integer>{

    Optional<Warehouse> findByWarehouseName(String warehouseName);
}
