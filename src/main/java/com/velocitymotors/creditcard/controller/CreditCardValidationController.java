package com.velocitymotors.creditcard.controller;

import com.velocitymotors.creditcard.api.CreditCardPaymentApi;
import com.velocitymotors.creditcard.api.model.PaymentStatusResponse;
import com.velocitymotors.creditcard.api.model.PaymentStatusRetrievalRequest;
import com.velocitymotors.creditcard.service.CreditCardValidationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/host/credit-card-payment-api")
public class CreditCardValidationController implements CreditCardPaymentApi {

    private static final Logger logger = LoggerFactory.getLogger(CreditCardValidationController.class);

    private final CreditCardValidationService creditCardValidationService;

    public CreditCardValidationController(CreditCardValidationService creditCardValidationService) {
        this.creditCardValidationService = creditCardValidationService;
    }

    @Override
    public ResponseEntity<PaymentStatusResponse> retrievePaymentStatus(
            PaymentStatusRetrievalRequest paymentStatusRetrievalRequest) {
        logger.info("Received payment status request");
        PaymentStatusResponse response = creditCardValidationService.retrievePaymentStatus(
                paymentStatusRetrievalRequest.getPaymentReference());
        logger.info("Returning payment status={}", response.getStatus());
        return ResponseEntity.ok(response);
    }
}
