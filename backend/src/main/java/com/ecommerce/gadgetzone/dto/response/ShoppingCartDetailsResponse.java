package com.ecommerce.gadgetzone.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingCartDetailsResponse {
    private int productId;
    private String productName;
    private String productPicture;
    private int amount;
    private double productPrice;
    
}
