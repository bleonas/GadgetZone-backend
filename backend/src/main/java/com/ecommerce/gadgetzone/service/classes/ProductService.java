package com.ecommerce.gadgetzone.service.classes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.ecommerce.gadgetzone.dto.response.ProductResponse;
import com.ecommerce.gadgetzone.entity.Product;
import com.ecommerce.gadgetzone.repository.ProductRepository;
import com.ecommerce.gadgetzone.service.interfaces.IProductService;

import lombok.RequiredArgsConstructor;

@Service
@CrossOrigin
@RequiredArgsConstructor
public class ProductService implements IProductService {
    private final ProductRepository productRepository;

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

}
