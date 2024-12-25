package com.ecommerce.gadgetzone.repository;

import com.ecommerce.gadgetzone.entity.WarehouseDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WarehouseDetailsRepository extends JpaRepository<WarehouseDetails,Integer>{
}
