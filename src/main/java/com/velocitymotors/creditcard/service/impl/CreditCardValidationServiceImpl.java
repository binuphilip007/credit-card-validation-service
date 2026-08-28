package com.velocitymotors.creditcard.service.impl;

import com.velocitymotors.creditcard.api.model.PaymentStatusResponse;
import com.velocitymotors.creditcard.service.CreditCardValidationService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Map;

@Service
public class CreditCardValidationServiceImpl implements CreditCardValidationService {

    private static final Map<String, PaymentStatusResponse.StatusEnum> PAYMENT_STATUSES = Map.of(
            "CC123456789", PaymentStatusResponse.StatusEnum.APPROVED,
            "CC987654321", PaymentStatusResponse.StatusEnum.REJECTED);

    @Override
    public PaymentStatusResponse retrievePaymentStatus(String paymentReference) {
        if (paymentReference == null || paymentReference.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "paymentReference is required");
        }

        PaymentStatusResponse.StatusEnum status = PAYMENT_STATUSES.get(paymentReference);
        if (status == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Payment not found");
        }

        return new PaymentStatusResponse()
                .lastUpdateDate(OffsetDateTime.now(ZoneOffset.UTC))
                .status(status);
    }
}
