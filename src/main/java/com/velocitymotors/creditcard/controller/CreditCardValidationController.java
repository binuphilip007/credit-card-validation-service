package com.velocitymotors.creditcard.controller;

import com.velocitymotors.creditcard.api.CreditCardPaymentApi;
import com.velocitymotors.creditcard.api.model.PaymentStatusResponse;
import com.velocitymotors.creditcard.api.model.PaymentStatusRetrievalRequest;
import com.velocitymotors.creditcard.service.CreditCardValidationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/host/credit-card-payment-api")
@RequiredArgsConstructor
@Slf4j
public class CreditCardValidationController implements CreditCardPaymentApi {

    private final CreditCardValidationService creditCardValidationService;

    @Override
    public ResponseEntity<PaymentStatusResponse> retrievePaymentStatus(
            PaymentStatusRetrievalRequest paymentStatusRetrievalRequest) {
        log.debug("Received payment status request");
        PaymentStatusResponse response = creditCardValidationService.retrievePaymentStatus(
                paymentStatusRetrievalRequest.getPaymentReference());
        log.debug("Returning payment status={}", response.getStatus());
        return ResponseEntity.ok(response);
    }
}
