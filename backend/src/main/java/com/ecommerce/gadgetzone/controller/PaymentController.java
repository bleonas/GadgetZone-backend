package com.ecommerce.gadgetzone.controller;

import com.ecommerce.gadgetzone.dto.request.PaymentRequest;
import com.ecommerce.gadgetzone.service.classes.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/add")
    public ResponseEntity<?> processPayment(@RequestBody PaymentRequest request) {
        try {
            paymentService.processPayment(request);
            return ResponseEntity.ok("Payment made successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}
