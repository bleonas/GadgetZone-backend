package com.ecommerce.gadgetzone.service.interfaces;

import java.util.List;

import com.ecommerce.gadgetzone.dto.request.BrandRequest;
import com.ecommerce.gadgetzone.dto.request.CategoryRequest;
import com.ecommerce.gadgetzone.dto.request.ProductRequest;
import com.ecommerce.gadgetzone.dto.request.UserSignUpRequest;
import com.ecommerce.gadgetzone.dto.request.WarehouseRequest;
import com.ecommerce.gadgetzone.dto.response.BrandResponse;
import com.ecommerce.gadgetzone.dto.response.CategoryResponse;
import com.ecommerce.gadgetzone.entity.Product;

public interface IAdminService {

    void registerAdmin(UserSignUpRequest userSignUpRequest);

    void addCategory(CategoryRequest addCategoryRequest);

    void addBrand(BrandRequest addBrandRequest);
    
    void addWarehouse(WarehouseRequest addWarehouseRequest);

    List<BrandResponse> getAllBrands();

    List<CategoryResponse> getAllCategories();

    void addProduct(ProductRequest addProductRequest);
    
    Product getProductById(int productId);
}
