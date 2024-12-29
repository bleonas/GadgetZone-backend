package com.ecommerce.gadgetzone.repository;

import com.ecommerce.gadgetzone.entity.Category;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Integer>{

    Optional<Category> findByCategoryName(String categoryName);

    Optional<Category> findByCategoryId(int categoryId);

}
