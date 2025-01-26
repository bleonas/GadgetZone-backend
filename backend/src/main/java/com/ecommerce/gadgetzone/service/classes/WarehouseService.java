package com.ecommerce.gadgetzone.service.classes;

import com.ecommerce.gadgetzone.dto.request.WarehouseDetailsRequest;
import com.ecommerce.gadgetzone.dto.request.WarehouseRequest;
import com.ecommerce.gadgetzone.entity.*;
import com.ecommerce.gadgetzone.repository.*;
import com.ecommerce.gadgetzone.service.interfaces.IWarehouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Optional;

@Service
@CrossOrigin
@RequiredArgsConstructor
public class WarehouseService implements IWarehouseService {

    private final WarehouseRepository warehouseRepository;
    private final WarehouseDetailsRepository warehouseDetailsRepository;
    private final ProductRepository productRepository;

    public void updateProductAmount(WarehouseDetailsRequest warehouseRequest) {
        Warehouse warehouse = warehouseRepository.findByWarehouseName("Gadget Zone");

        Product product = productRepository.findByProductId(warehouseRequest.getProductId())
                .orElseThrow(() -> new IllegalStateException("Product not found"));

        int amount = warehouseRequest.getAmount();

        WarehouseDetails existingDetails = warehouseDetailsRepository.findByWarehouseAndProduct(
                Optional.ofNullable(warehouse), product
        );

        if (existingDetails != null) {
            existingDetails.setAmount(existingDetails.getAmount() + amount); // Add the provided amount
            warehouseDetailsRepository.save(existingDetails);
        } else {
            WarehouseDetails newDetail = new WarehouseDetails();
            newDetail.setAmount(amount); // Set the amount from the request
            newDetail.setProduct(product);
            newDetail.setWarehouse(warehouse);
            warehouseDetailsRepository.save(newDetail);
        }
    }

}
