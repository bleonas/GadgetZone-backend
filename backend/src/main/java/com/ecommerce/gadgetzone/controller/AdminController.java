package com.ecommerce.gadgetzone.controller;

import java.io.IOException;
import java.util.List;

import com.ecommerce.gadgetzone.dto.request.*;
import com.ecommerce.gadgetzone.entity.*;
import com.ecommerce.gadgetzone.service.interfaces.IWarehouseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.gadgetzone.config.JwtService;
import com.ecommerce.gadgetzone.dto.response.BrandResponse;
import com.ecommerce.gadgetzone.dto.response.CategoryResponse;
import com.ecommerce.gadgetzone.dto.response.AllOrdersResponse;
import com.ecommerce.gadgetzone.dto.response.ProductResponse;
import com.ecommerce.gadgetzone.dto.response.UserLogInResponse;
import com.ecommerce.gadgetzone.service.interfaces.IAdminService;
import com.ecommerce.gadgetzone.service.interfaces.IOrderService;
import com.ecommerce.gadgetzone.service.interfaces.IUserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final IAdminService adminService;
    private final IUserService userService;
    private final IWarehouseService warehouseService;
    private final IOrderService orderService;
    
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

    @CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
    @PostMapping(value = "/add-product", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> addProduct(@ModelAttribute ProductRequest addProductRequest) {
        try {
            adminService.addProduct(addProductRequest);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return ResponseEntity.ok("Product Added Successfully");
    }

    @CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
    @GetMapping("/add-products/{productId}")
    public ResponseEntity<?> getProductById(@PathVariable int productId) {
        Product product = adminService.getProductById(productId);
        if (product == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }
        return ResponseEntity.ok(product);
    }

    @CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
    @PutMapping("/edit-product/{productId}")
    public ResponseEntity<?> editProduct(@PathVariable("productId") int productId, @RequestBody ProductRequest productRequest) {
        adminService.editProduct(productId, productRequest);
        return ResponseEntity.ok("Product updated successfully");
    }

    @CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
    @GetMapping("/products-list")
    public ResponseEntity<List<ProductResponse>> getProducts() {
        try {
            List<ProductResponse> products = adminService.getAllProducts();
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
    @DeleteMapping("/products-list/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable("productId") int productId) {
        try {
            adminService.deleteProductById(productId);
            return ResponseEntity.ok("Product deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error deleting product: " + e.getMessage());
        }
    }

    @CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
    @PutMapping("/add-product-amount/{productId}")
    public ResponseEntity<?> editProductAmount(@RequestBody WarehouseDetailsRequest warehouseRequest){
        warehouseService.updateProductAmount(warehouseRequest);
        return ResponseEntity.ok("Product amount updated successfully!");
    }

    @CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
    @GetMapping("/orders")
    public List<AllOrdersResponse> getAllOrders() {
        return orderService.getAllOrders();
    }

}
