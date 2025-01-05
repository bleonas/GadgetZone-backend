package com.ecommerce.gadgetzone.dto.response;

import com.ecommerce.gadgetzone.entity.Category;
import com.ecommerce.gadgetzone.enums.ProductStatus;
import com.ecommerce.gadgetzone.entity.Brand;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Builder
@ToString
public class ProductResponse {
    private int productId;
    private String productName;
    private String productDescription;
    private String productPicture;
    private double productPrice;
    private Brand brand;
    private Category category;
    private ProductStatus status;

}
