package com.ecommerce.gadgetzone.service.interfaces;

import com.ecommerce.gadgetzone.dto.request.PaymentRequest;
import com.stripe.exception.StripeException;

public interface IPaymentService {

    void processPayment(PaymentRequest request) throws StripeException;
}
