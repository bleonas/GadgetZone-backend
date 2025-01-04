package com.ecommerce.gadgetzone.service.classes;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.ecommerce.gadgetzone.service.interfaces.IImageService;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ecommerce.gadgetzone.dto.request.BrandRequest;
import com.ecommerce.gadgetzone.dto.request.CategoryRequest;
import com.ecommerce.gadgetzone.dto.request.ProductRequest;
import com.ecommerce.gadgetzone.dto.request.UserSignUpRequest;
import com.ecommerce.gadgetzone.dto.request.WarehouseRequest;
import com.ecommerce.gadgetzone.dto.response.BrandResponse;
import com.ecommerce.gadgetzone.dto.response.CategoryResponse;
import com.ecommerce.gadgetzone.dto.response.ProductResponse;
import com.ecommerce.gadgetzone.dto.response.UserLogInResponse;
import com.ecommerce.gadgetzone.entity.Brand;
import com.ecommerce.gadgetzone.entity.Category;
import com.ecommerce.gadgetzone.entity.Product;
import com.ecommerce.gadgetzone.entity.User;
import com.ecommerce.gadgetzone.entity.Warehouse;
import com.ecommerce.gadgetzone.enums.Role;
import com.ecommerce.gadgetzone.repository.BrandRepository;
import com.ecommerce.gadgetzone.repository.CategoryRepository;
import com.ecommerce.gadgetzone.repository.ProductRepository;
import com.ecommerce.gadgetzone.repository.UserRepository;
import com.ecommerce.gadgetzone.repository.WarehouseRepository;
import com.ecommerce.gadgetzone.service.interfaces.IAdminService;

import lombok.RequiredArgsConstructor;

@Service
@CrossOrigin
@RequiredArgsConstructor
public class AdminService implements IAdminService{

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final PasswordEncoder passwordEncoder;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;
    private final WarehouseRepository warehouseRepository;
    private final IImageService imageService;


    @Transactional
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

    @Transactional
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

    @Transactional
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

    @Transactional
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
                        .brandId(brand.getBrandId())
                        .brandName(brand.getNameBrand())
                        .build())
                .collect(Collectors.toList()); 
    }

    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(category -> CategoryResponse.builder()
                        .categoryId(category.getCategoryId())
                        .categoryName(category.getCategoryName())
                        .build())
                .collect(Collectors.toList()); 
    }

    @Transactional
    public ResponseEntity<?> addProduct(ProductRequest addProductRequest) {
        Optional<Product> existingProduct = productRepository.findByProductName(addProductRequest.getProductName());
        if (existingProduct.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Product already exists");
        }

        Brand brand = brandRepository.findByBrandId(addProductRequest.getBrand().getBrandId())
            .orElseThrow(() -> new IllegalStateException("Brand not found"));

        Category category = categoryRepository.findByCategoryId(addProductRequest.getCategory().getCategoryId())
            .orElseThrow(() -> new IllegalStateException("Category not found"));

        Product newProduct = Product.builder()
            .productName(addProductRequest.getProductName())
            .productDescription(addProductRequest.getProductDescription())
            .productPrice(addProductRequest.getProductPrice())
            .brand(brand)
            .category(category)
            .status(addProductRequest.getStatus())
            .build();


        if(addProductRequest.getProductPicture() != null){
            File path = new File("src/main/resources/static/img/products");

            String imageName = newProduct.getProductName();

            try {
                String newProfilePhotoPath = imageService.saveImage(path, addProductRequest.getProductPicture(), imageName);
                newProduct.setProductPicture(newProfilePhotoPath);
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ruajtja e produktit deshtoi!");
            }
        }
        productRepository.save(newProduct);
        return ResponseEntity.status(HttpStatus.OK).body("Produkti u shtua me sukses!");
    }



    public Product getProductById(int productId) {
        return productRepository.findById(productId).orElse(null);
    }


    @Transactional
    public void editProduct(int productId, ProductRequest productRequest) {
        Product existingProduct = productRepository.findByProductId(productId)
                .orElseThrow(() -> new IllegalStateException("Product not found"));
    
            Brand brand = brandRepository.findByBrandId(productRequest.getBrand().getBrandId())
                .orElseThrow(() -> new IllegalStateException("Brand not found"));
    
            Category category = categoryRepository.findByCategoryId(productRequest.getCategory().getCategoryId())
                .orElseThrow(() -> new IllegalStateException("Category not found"));

        existingProduct.setProductName(productRequest.getProductName());
        existingProduct.setProductDescription(productRequest.getProductDescription());
        existingProduct.setProductPrice(productRequest.getProductPrice());
        existingProduct.setStatus(productRequest.getStatus());
        existingProduct.setBrand(brand);
        existingProduct.setCategory(category);
        existingProduct.setStatus(productRequest.getStatus());
        productRepository.save(existingProduct);
    }

        public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(product -> ProductResponse.builder()
                    .productId(product.getProductId())
                    .productName(product.getProductName())
                    .productDescription(product.getProductDescription())
                    .productPicture(product.getProductPicture())
                    .productPrice(product.getProductPrice())
                    .status(product.getStatus())
                    .brand(product.getBrand())
                    .category(product.getCategory())
                    .build())

                    .collect(Collectors.toList());
    }

    public void deleteProductById(int productId) {
        Product product = productRepository.findByProductId(productId)
                                  .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        productRepository.delete(product);
    }


}
