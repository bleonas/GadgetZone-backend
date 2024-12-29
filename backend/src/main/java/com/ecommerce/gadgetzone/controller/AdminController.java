package com.ecommerce.gadgetzone.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.gadgetzone.config.JwtService;
import com.ecommerce.gadgetzone.dto.request.BrandRequest;
import com.ecommerce.gadgetzone.dto.request.CategoryRequest;
import com.ecommerce.gadgetzone.dto.request.UserSignUpRequest;
import com.ecommerce.gadgetzone.dto.request.WarehouseRequest;
import com.ecommerce.gadgetzone.dto.response.BrandResponse;
import com.ecommerce.gadgetzone.dto.response.CategoryResponse;
import com.ecommerce.gadgetzone.dto.response.UserLogInResponse;
import com.ecommerce.gadgetzone.entity.Brand;
import com.ecommerce.gadgetzone.entity.Category;
import com.ecommerce.gadgetzone.entity.Warehouse;
import com.ecommerce.gadgetzone.service.interfaces.IAdminService;
import com.ecommerce.gadgetzone.service.interfaces.IUserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final IAdminService adminService;
    private final IUserService userService;

    @CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true") 
    @PostMapping("/create-admin")
    public ResponseEntity<?> createAdmin(@RequestBody UserSignUpRequest createAdminRequest) {
        try {
            adminService.registerAdmin(createAdminRequest);
                return ResponseEntity.ok("Admin Registered Successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error during logout: " + e.getMessage());
        }
    }

    @CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true") 
    @PostMapping("/category")
    public ResponseEntity<?> addCategory(@RequestBody CategoryRequest addCategoryRequest) {
        adminService.addCategory(addCategoryRequest);
        return ResponseEntity.ok("Category Registered Successfully");
    }

    @CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true") 
    @PostMapping("/brand")
    public ResponseEntity<?> addBrand(@RequestBody BrandRequest addBrandRequest) {
        adminService.addBrand(addBrandRequest);
        return ResponseEntity.ok("Brand Registered Successfully");
    }

    @CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true") 
    @PostMapping("/warehouse")
    public ResponseEntity<?> addWarehouse(@RequestBody WarehouseRequest addWarehouseRequest) {
        adminService.addWarehouse(addWarehouseRequest);
        return ResponseEntity.ok("Warehouse Registered Successfully");
    }

    @CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
    @GetMapping("/brand")
    public List<BrandResponse> getBrands() {
        return adminService.getAllBrands(); 
    }

    @CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
    @GetMapping("/category")
    public List<CategoryResponse> getCategories() {
        return adminService.getAllCategories(); 
    }

    @CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
    @GetMapping("/users")
    public ResponseEntity<List<UserLogInResponse>> getUsers() {
        try {
            List<UserLogInResponse> users = userService.getAllUsers();
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
    @DeleteMapping("/users/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable("userId") int userId) {
        try {
            userService.deleteUserById(userId);
            return ResponseEntity.ok("User deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error deleting user: " + e.getMessage());
        }
    }

}
