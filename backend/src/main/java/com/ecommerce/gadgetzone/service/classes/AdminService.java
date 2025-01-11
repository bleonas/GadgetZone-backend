package com.ecommerce.gadgetzone.service.classes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

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
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${file.upload-dir}")
    private String UPLOAD_DIR;


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

    public void addProduct(ProductRequest addProductRequest) throws IOException {
        Optional<Product> existingProduct = productRepository.findByProductName(addProductRequest.getProductName());
        if (existingProduct.isPresent()) {
            throw new IllegalStateException("Product already exists");
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

        // List to store image paths (filenames)
        List<String> imagePaths = new ArrayList<>();
        MultipartFile[] images = addProductRequest.getProductPictures();
        
        // Ensure the uploads directory exists
        Path uploadDir = Paths.get(UPLOAD_DIR);
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);  // Create the directory if not exists
        }

        // Handle each uploaded image file
        for (MultipartFile image : images) {
            try {
                // Generate a unique filename for the image (e.g., based on timestamp)
                String filename = System.currentTimeMillis() + "-" + image.getOriginalFilename();
                Path filePath = uploadDir.resolve(filename);

                // Save the image to the file system
                image.transferTo(filePath.toFile());

                // Add the filename (path) to the list of image paths
                imagePaths.add(filename);

            } catch (IOException e) {
                System.err.println("Error saving file: " + e.getMessage());
                throw new IOException("Error saving file: " + e.getMessage(), e);
            }
        }

        // Convert the list of image paths to a JSON string
        String imagePathsJson = convertToJson(imagePaths);

        // Set image paths as a JSON string in the product object
        newProduct.setProductPicture(imagePathsJson);

        // Save the product to the database
        productRepository.save(newProduct);

    }

    public Product getProductById(int productId) {
        return productRepository.findById(productId).orElse(null);
    }

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

         // Handle updating product pictures
    if (productRequest.getProductPictures() != null && productRequest.getProductPictures().length > 0) {
        List<String> filePaths = new ArrayList<>();
        for (MultipartFile file : productRequest.getProductPictures()) {
            String fileName = System.currentTimeMillis() + "-" + file.getOriginalFilename();
            Path filePath = Paths.get(UPLOAD_DIR, fileName);
            try {
                Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
                filePaths.add(fileName); // Add the file name to the list
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Error saving file: " + fileName);
            }
        }

        // Convert the file paths to a JSON string
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonPaths = objectMapper.writeValueAsString(filePaths);
            existingProduct.setProductPicture(jsonPaths); // Update the product picture field
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException("Error converting file paths to JSON");
        }
    }
        productRepository.save(existingProduct);
    }


    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();
        ObjectMapper objectMapper = new ObjectMapper(); // JSON object mapper
    
        return products.stream()
                .map(product -> {
                    List<String> productPictures = new ArrayList<>();
                    try {
                        // Deserialize JSON string into a list
                        if (product.getProductPicture() != null) {
                            productPictures = objectMapper.readValue(product.getProductPicture(), List.class);
                            product.setProductPicture(convertToJson(productPictures));  // Convert back to JSON string and set the string
                        }
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                        // Handle exception: Log error or set an empty list as fallback
                    }
    
                    return ProductResponse.builder()
                            .productId(product.getProductId())
                            .productName(product.getProductName())
                            .productDescription(product.getProductDescription())
                            .productPictures(product.getProductPicture()) // Set deserialized product pictures
                            .productPrice(product.getProductPrice())
                            .status(product.getStatus())
                            .brand(product.getBrand())
                            .category(product.getCategory())
                            .build();
                })
                .collect(Collectors.toList());
    }

    public void deleteProductById(int productId) {
        Product product = productRepository.findByProductId(productId)
                                  .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        productRepository.delete(product);
    }

    private String convertToJson(List<String> imagePaths) throws JsonProcessingException {
        // Convert the list of image paths to a JSON string
        return objectMapper.writeValueAsString(imagePaths);
    }
}