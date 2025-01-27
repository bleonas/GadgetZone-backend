package com.ecommerce.gadgetzone.service.classes;


import com.ecommerce.gadgetzone.dto.request.PaymentRequest;
import com.ecommerce.gadgetzone.entity.Payment;
import com.ecommerce.gadgetzone.entity.Order;
import com.ecommerce.gadgetzone.entity.User;
import com.ecommerce.gadgetzone.enums.PaymentStatus;
import com.ecommerce.gadgetzone.repository.PaymentRepository;
import com.ecommerce.gadgetzone.repository.OrderRepository;
import com.ecommerce.gadgetzone.repository.UserRepository;
import com.ecommerce.gadgetzone.service.interfaces.IPaymentService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService implements IPaymentService {

    @Value("${stripe.api.key}")
    private String stripeApiKey;

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public void processPayment(PaymentRequest request) throws StripeException {
        Stripe.apiKey = stripeApiKey;

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Find the order
        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order not found"));


        // Create PaymentIntent with Stripe
        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setCurrency(request.getCurrency())
                .setAmount((long) order.getTotalPrice())
                .putMetadata("order_id", String.valueOf(order.getOrderId()))
                .putMetadata("user_id", String.valueOf(user.getUserId()))
                .build();

        PaymentIntent paymentIntent = PaymentIntent.create(params);

        // Save payment record in the database
        Payment payment = new Payment();
        payment.setMethod(request.getMethod());
        payment.setStatus(PaymentStatus.PAGUAR);
        payment.setTransactionId(paymentIntent.getId());
        payment.setUser(user);
        payment.setOrder(order);
        paymentRepository.save(payment);

    }
}

