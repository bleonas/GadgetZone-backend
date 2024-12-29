package com.ecommerce.gadgetzone.service.classes;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.ecommerce.gadgetzone.dto.request.BrandRequest;
import com.ecommerce.gadgetzone.dto.request.CategoryRequest;
import com.ecommerce.gadgetzone.dto.request.UserSignUpRequest;
import com.ecommerce.gadgetzone.dto.request.WarehouseRequest;
import com.ecommerce.gadgetzone.dto.response.BrandResponse;
import com.ecommerce.gadgetzone.dto.response.CategoryResponse;
import com.ecommerce.gadgetzone.entity.Brand;
import com.ecommerce.gadgetzone.entity.Category;
import com.ecommerce.gadgetzone.entity.User;
import com.ecommerce.gadgetzone.entity.Warehouse;
import com.ecommerce.gadgetzone.enums.Role;
import com.ecommerce.gadgetzone.repository.BrandRepository;
import com.ecommerce.gadgetzone.repository.CategoryRepository;
import com.ecommerce.gadgetzone.repository.UserRepository;
import com.ecommerce.gadgetzone.repository.WarehouseRepository;
import com.ecommerce.gadgetzone.service.interfaces.IAdminService;

import lombok.RequiredArgsConstructor;

@Service
@CrossOrigin
@RequiredArgsConstructor
public class AdminService implements IAdminService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;
    private final WarehouseRepository warehouseRepository;

    public void registerAdmin(UserSignUpRequest userSignUpRequest) {
    Optional<User> existingUser = userRepository.findByEmail(userSignUpRequest.getEmail());
    if (existingUser.isPresent()) {
        throw new IllegalStateException("Email already taken");
    }
    User admin = User.builder()
            .email(userSignUpRequest.getEmail())
            .password(passwordEncoder.encode(userSignUpRequest.getPassword())) 
            .firstName(userSignUpRequest.getFirstName())
            .lastName(userSignUpRequest.getLastName())
            .role(Role.ADMIN)
            .build();

        userRepository.save(admin);
    }

    public void addCategory(CategoryRequest addCategoryRequest) {
        Optional<Category> existingCategory = categoryRepository.findByCategoryName(addCategoryRequest.getCategoryName());
        if (existingCategory.isPresent()) {
            throw new IllegalStateException("Category already exists");
        }

        Category newCategory = Category.builder()
                .categoryName(addCategoryRequest.getCategoryName())
                .build();

            categoryRepository.save(newCategory);
    }

    public void addBrand(BrandRequest addBrandRequest) {
        Optional<Brand> existingBrand = brandRepository.findByNameBrand(addBrandRequest.getBrandName());
        if (existingBrand.isPresent()) {
            throw new IllegalStateException("Brand already exists");
        }

        Brand newBrand = Brand.builder()
                .nameBrand(addBrandRequest.getBrandName())
                .build();

            brandRepository.save(newBrand);
    }

    public void addWarehouse(WarehouseRequest addWarehouseRequest) {
        Optional<Warehouse> existingWarehouse = warehouseRepository.findByWarehouseName(addWarehouseRequest.getWarehouseName());
        if (existingWarehouse.isPresent()) {
            throw new IllegalStateException("Warehouse already exists");
        }

        Warehouse newWarehouse = Warehouse.builder()
                .warehouseName(addWarehouseRequest.getWarehouseName())
                .build();

            warehouseRepository.save(newWarehouse);
    }

    public List<BrandResponse> getAllBrands() {
        return brandRepository.findAll().stream()
                .map(brand -> BrandResponse.builder()
                        .brandName(brand.getNameBrand())
                        .build())
                .collect(Collectors.toList()); 
    }

    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(category -> CategoryResponse.builder()
                        .categoryName(category.getCategoryName())
                        .build())
                .collect(Collectors.toList()); 
    }

}
