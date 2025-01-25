package com.ecommerce.gadgetzone.repository;

import com.ecommerce.gadgetzone.entity.Product;
import com.ecommerce.gadgetzone.entity.Warehouse;
import com.ecommerce.gadgetzone.entity.WarehouseDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WarehouseDetailsRepository extends JpaRepository<WarehouseDetails,Integer>{

    WarehouseDetails findByWarehouseAndProduct(Optional<Warehouse> warehouse, Product product);
}
