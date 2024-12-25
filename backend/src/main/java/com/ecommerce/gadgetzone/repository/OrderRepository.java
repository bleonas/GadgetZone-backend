package com.ecommerce.gadgetzone.repository;

import com.ecommerce.gadgetzone.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order,Integer>{
}
