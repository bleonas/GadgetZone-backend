package com.ecommerce.gadgetzone.dto.request;


import lombok.*;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {
    private int userId; // User ID
    private int orderId; // Order ID
    private String method; // Payment method (e.g., "stripe")
    private String currency;
}
