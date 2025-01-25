package com.ecommerce.gadgetzone.dto.request;


import lombok.*;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailRequest {
    private int productId;
    private int amount;
}
