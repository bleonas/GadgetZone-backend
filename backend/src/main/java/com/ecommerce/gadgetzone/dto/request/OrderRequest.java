package com.ecommerce.gadgetzone.dto.request;

import com.ecommerce.gadgetzone.entity.OrderDetails;
import com.ecommerce.gadgetzone.entity.Product;
import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
    private int userId;
    private List<OrderDetailRequest> products;
    private double totalPrice;
}
