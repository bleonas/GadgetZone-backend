package com.ecommerce.gadgetzone.repository;

import com.ecommerce.gadgetzone.entity.Order;
import com.ecommerce.gadgetzone.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order,Integer>{
    Optional<Order> findByUser(User user);

    List<Order> findAllByUser(User user);

}
