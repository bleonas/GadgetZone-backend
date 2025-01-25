package com.ecommerce.gadgetzone.controller;

import com.ecommerce.gadgetzone.dto.request.OrderRequest;
import com.ecommerce.gadgetzone.dto.response.OrderResponse;
import com.ecommerce.gadgetzone.service.interfaces.IOrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final IOrderService orderService;


    @CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
    @PostMapping("/{userId}")
    public ResponseEntity<?> addOrder(@PathVariable int userId, @RequestBody OrderRequest orderRequest){
        try {
            orderService.addOrder(orderRequest, userId);
            return ResponseEntity.ok("Order placed successfully");
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
    @GetMapping("/{userId}")
    public ResponseEntity<Map<String, Object>> getOrdersByUser(@PathVariable int userId) {
        List<OrderResponse> orders = orderService.getOrders(userId);
        Map<String, Object> response = new HashMap<>();
        response.put("orders", orders);
        return ResponseEntity.ok(response);
    }

    @CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
    @DeleteMapping("/{userId}/{orderId}")
    public ResponseEntity<?> deleteOrder(@PathVariable int userId,
                                         @PathVariable int orderId){
        try {
            orderService.removeOrder(userId,orderId);
            return ResponseEntity.ok("Orders and associated details deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting orders: " + e.getMessage());
        }
    }

}
