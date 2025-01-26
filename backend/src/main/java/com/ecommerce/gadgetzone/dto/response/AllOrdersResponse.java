package com.ecommerce.gadgetzone.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AllOrdersResponse {
    private String orderId; 
    private OrderUser user;
    private List<OrderProduct> products;
    private double totalAmount;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OrderUser {
        private String name;
        private String role;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OrderProduct {
        private String productId; 
        private String productName;
        private int quantity;
    }
}
