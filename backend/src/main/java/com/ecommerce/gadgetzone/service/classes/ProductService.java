package com.ecommerce.gadgetzone.service.classes;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.ecommerce.gadgetzone.dto.response.ProductResponse;
import com.ecommerce.gadgetzone.entity.Product;
import com.ecommerce.gadgetzone.repository.ProductRepository;
import com.ecommerce.gadgetzone.service.interfaces.IProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@CrossOrigin
@RequiredArgsConstructor
public class ProductService implements IProductService {
    private final ProductRepository productRepository;

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

    private String convertToJson(List<String> imagePaths) throws JsonProcessingException {
        // Convert the list of image paths to a JSON string
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(imagePaths);
    }

}
