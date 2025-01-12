package com.ecommerce.gadgetzone.repository;

import com.ecommerce.gadgetzone.entity.Product;
import com.ecommerce.gadgetzone.entity.ShoppingCart;
import com.ecommerce.gadgetzone.entity.ShoppingCartDetails;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingCartDetailsRepository extends JpaRepository<ShoppingCartDetails,Integer>{
    List<ShoppingCartDetails> findByShoppingCart(ShoppingCart shoppingCart);

    Optional<ShoppingCartDetails> findByShoppingCartAndProduct(ShoppingCart shoppingCart, Product product);
    
}
