package com.ecommerce.gadgetzone.service.interfaces;

import java.util.List;

import com.ecommerce.gadgetzone.dto.response.ProductResponse;

public interface IProductService {

    List<ProductResponse> getAllProducts();
}
