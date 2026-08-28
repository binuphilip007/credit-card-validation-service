package com.velocitymotors.creditcard.controller;

import com.velocitymotors.creditcard.api.CreditCardPaymentApi;
import com.velocitymotors.creditcard.api.model.PaymentStatusResponse;
import com.velocitymotors.creditcard.api.model.PaymentStatusRetrievalRequest;
import com.velocitymotors.creditcard.service.CreditCardValidationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/host/credit-card-payment-api")
public class CreditCardValidationController implements CreditCardPaymentApi {

    private final CreditCardValidationService creditCardValidationService;

    public CreditCardValidationController(CreditCardValidationService creditCardValidationService) {
        this.creditCardValidationService = creditCardValidationService;
    }

    @Override
    public ResponseEntity<PaymentStatusResponse> retrievePaymentStatus(
            PaymentStatusRetrievalRequest paymentStatusRetrievalRequest) {
        PaymentStatusResponse response = creditCardValidationService.retrievePaymentStatus(
                paymentStatusRetrievalRequest.getPaymentReference());
        return ResponseEntity.ok(response);
    }
}
