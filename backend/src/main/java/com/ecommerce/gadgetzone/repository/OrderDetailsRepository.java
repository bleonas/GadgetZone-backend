package com.ecommerce.gadgetzone.repository;

import com.ecommerce.gadgetzone.entity.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailsRepository extends JpaRepository<OrderDetails,Integer>{
}
