package com.ecommerce.gadgetzone.repository;

import com.ecommerce.gadgetzone.entity.ShoppingCart;
import com.ecommerce.gadgetzone.entity.User;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart,Integer> {
    Optional<ShoppingCart> findByUser(User user);

}
