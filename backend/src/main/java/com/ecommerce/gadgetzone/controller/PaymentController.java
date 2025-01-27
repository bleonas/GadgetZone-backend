package com.ecommerce.gadgetzone.controller;

import com.ecommerce.gadgetzone.dto.request.PaymentRequest;
import com.ecommerce.gadgetzone.service.classes.PaymentService;
import com.ecommerce.gadgetzone.service.interfaces.IPaymentService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {
    
    private final IPaymentService paymentService;

    @CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
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
