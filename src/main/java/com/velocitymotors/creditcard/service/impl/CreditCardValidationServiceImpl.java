package com.velocitymotors.creditcard.service.impl;

import com.velocitymotors.creditcard.api.model.PaymentStatusResponse;
import com.velocitymotors.creditcard.model.entity.PaymentCard;
import com.velocitymotors.creditcard.repository.PaymentCardRepository;
import com.velocitymotors.creditcard.service.CreditCardValidationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreditCardValidationServiceImpl implements CreditCardValidationService {

    private final PaymentCardRepository paymentCardRepository;

    @Override
    public PaymentStatusResponse retrievePaymentStatus(String paymentReference) {
        if (paymentReference == null || paymentReference.isBlank()) {
            log.warn("Rejected request with blank paymentReference");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "paymentReference is required");
        }

        log.debug("Looking up payment status");
        PaymentCard paymentCard = paymentCardRepository.findByPaymentReference(paymentReference)
                .orElseThrow(() -> {
                    log.warn("Payment not found");
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Payment not found");
                });

        log.debug("Resolved payment status={}", paymentCard.getStatus());
        return new PaymentStatusResponse()
                .lastUpdateDate(OffsetDateTime.now(ZoneOffset.UTC))
                .status(paymentCard.getStatus());
    }
}

