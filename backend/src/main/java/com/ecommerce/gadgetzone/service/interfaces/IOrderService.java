package com.ecommerce.gadgetzone.service.interfaces;

import com.ecommerce.gadgetzone.dto.request.OrderRequest;
import com.ecommerce.gadgetzone.dto.response.OrderResponse;
import com.ecommerce.gadgetzone.dto.response.AllOrdersResponse;

import java.util.List;

public interface IOrderService {
    void addOrder(OrderRequest orderRequest,int userId);

    List<OrderResponse> getOrders(int userId);

    void removeOrder(int userId,int orderId);

    List<AllOrdersResponse> getAllOrders();
}
