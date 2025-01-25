package com.ecommerce.gadgetzone.dto.response;


import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
    private int orderId;
    private List<OrderDetailsResponse> orderItems;
    private double totalPrice;
}
