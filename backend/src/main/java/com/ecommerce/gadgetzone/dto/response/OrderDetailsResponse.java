package com.ecommerce.gadgetzone.dto.response;


import lombok.*;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailsResponse {
    private String productName;
    private int productId;
    private int amount;
}
