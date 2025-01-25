package com.ecommerce.gadgetzone.repository;

import com.ecommerce.gadgetzone.entity.Order;
import com.ecommerce.gadgetzone.entity.OrderDetails;
import com.ecommerce.gadgetzone.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderDetailsRepository extends JpaRepository<OrderDetails,Integer>{
    List<OrderDetails> findByOrder(Order order);

    Optional<OrderDetails> findByOrderAndProduct(Order order, Product product);

    Optional<OrderDetails> findByProduct(Product product);




}
