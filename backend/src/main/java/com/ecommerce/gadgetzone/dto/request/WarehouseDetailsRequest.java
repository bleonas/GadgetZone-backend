package com.ecommerce.gadgetzone.dto.request;


import lombok.*;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WarehouseDetailsRequest {
    private int productId;
    private int amount;
}
