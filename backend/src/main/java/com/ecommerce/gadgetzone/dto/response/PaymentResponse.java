package com.ecommerce.gadgetzone.dto.response;

import lombok.*;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponse {

    private int userId; // User ID
    private int orderId; // Order ID
    private String method; // Payment method (e.g., "stripe")
    private String currency;
    private int amount;
}
