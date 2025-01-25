package com.ecommerce.gadgetzone.service.interfaces;

import com.ecommerce.gadgetzone.dto.request.OrderRequest;
import com.ecommerce.gadgetzone.dto.response.OrderResponse;

import java.util.List;

public interface IOrderService {
    void addOrder(OrderRequest orderRequest,int userId);

    List<OrderResponse> getOrders(int userId);

    void removeOrder(int userId,int orderId);
}
