package com.ecommerce.gadgetzone.controller;

import com.ecommerce.gadgetzone.dto.request.ShoppingCartRequest;
import com.ecommerce.gadgetzone.dto.response.ShoppingCartDetailsResponse;
import com.ecommerce.gadgetzone.dto.response.ShoppingCartResponse;
import com.ecommerce.gadgetzone.service.interfaces.IShoppingCartService;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class ShoppingCartController {

    private final IShoppingCartService cartService;

    @CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true") 
    @PostMapping("/add-to-cart")
    public ResponseEntity<?> addToCart(@RequestBody ShoppingCartRequest cartRequest) {
        cartService.addToCart(cartRequest);
        return ResponseEntity.ok().body("Product added to cart successfully");
    }


    @CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true") 
    @GetMapping("/{userId}")
    public ShoppingCartResponse getCart(@PathVariable int userId) {
        List<ShoppingCartDetailsResponse> cartItems = cartService.getCartDetails(userId);
        double totalCartPrice = cartService.getTotalCartPrice(userId);
        ShoppingCartResponse shoppingCartResponse = new ShoppingCartResponse();
        shoppingCartResponse.setCartItems(cartItems);
        shoppingCartResponse.setTotalCartPrice(totalCartPrice);   
    return shoppingCartResponse;
    }

    @CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
    @PutMapping("/{userId}")
    public ResponseEntity<?> updateCart(@PathVariable int userId, @RequestBody Map<Integer, Integer> quantities) {
        cartService.updateCart(userId, quantities);
        return ResponseEntity.ok().body("Cart updated successfully");
    }

    @CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
    @DeleteMapping("/{userId}/{productId}")
    public ResponseEntity<?> removeItem(@PathVariable int userId, @PathVariable int productId) {
        try {
            cartService.removeItemFromCart(userId, productId);
            return ResponseEntity.ok().body("Item removed from cart successfully");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error removing item from cart: " + e.getMessage());
        }
    }

}
