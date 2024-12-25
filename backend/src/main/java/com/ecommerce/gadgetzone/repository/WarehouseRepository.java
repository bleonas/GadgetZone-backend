package com.ecommerce.gadgetzone.repository;

import com.ecommerce.gadgetzone.entity.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse,Integer>{
}
