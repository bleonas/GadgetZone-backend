package com.ecommerce.gadgetzone.dto.response;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingCartResponse {
    private List<ShoppingCartDetailsResponse> cartItems;
    private double totalCartPrice;    
    
}
