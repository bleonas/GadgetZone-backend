package com.ecommerce.gadgetzone.repository;

import com.ecommerce.gadgetzone.entity.ShoppingCartDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingCartDetailsRepository extends JpaRepository<ShoppingCartDetails,Integer>{
}
