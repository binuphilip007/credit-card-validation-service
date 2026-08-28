package com.velocitymotors.creditcard.service;

import com.velocitymotors.creditcard.api.model.PaymentStatusResponse;

public interface CreditCardValidationService {

    PaymentStatusResponse retrievePaymentStatus(String paymentReference);
}
