package com.ecommerce.gadgetzone.dto.request;

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
public class ProductRequest {
    private int productId;
    private String productName;
    private String productDescription;
    private MultipartFile productPicture;
    private double productPrice;
    private Brand brand;
    private Category category;
    private ProductStatus status;
}
