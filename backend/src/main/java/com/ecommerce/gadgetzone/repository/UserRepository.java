package com.ecommerce.gadgetzone.repository;

import com.ecommerce.gadgetzone.entity.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    
    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
    Optional<User> findByUserId(int userId); 
}
