package com.ecommerce.gadgetzone.service.interfaces;

import java.util.List;
import java.util.Map;

import com.ecommerce.gadgetzone.dto.request.ShoppingCartRequest;
import com.ecommerce.gadgetzone.dto.response.ShoppingCartDetailsResponse;

public interface IShoppingCartService {

    void addToCart(ShoppingCartRequest cartRequest);

    List<ShoppingCartDetailsResponse> getCartDetails(int userId);

    double getTotalCartPrice(int userId);

    void updateCart(int userId, Map<Integer, Integer> quantities);

    void removeItemFromCart(int userId, int productId);
}
