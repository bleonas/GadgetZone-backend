package com.ecommerce.gadgetzone.service.interfaces;

import com.ecommerce.gadgetzone.dto.request.WarehouseDetailsRequest;
import com.ecommerce.gadgetzone.dto.request.WarehouseRequest;

public interface IWarehouseService {

    void updateProductAmount(WarehouseDetailsRequest warehouseRequest);
}
