package com.ecommerce.gadgetzone.service.interfaces;

import com.ecommerce.gadgetzone.dto.request.BrandRequest;
import com.ecommerce.gadgetzone.dto.request.CategoryRequest;
import com.ecommerce.gadgetzone.dto.request.UserSignUpRequest;
import com.ecommerce.gadgetzone.dto.request.WarehouseRequest;

public interface IAdminService {

    void registerAdmin(UserSignUpRequest userSignUpRequest);

    void addCategory(CategoryRequest addCategoryRequest);

    void addBrand(BrandRequest addBrandRequest);
    
    void addWarehouse(WarehouseRequest addWarehouseRequest);
    
}
