package com.velocitymotors.creditcard.service.impl;

import com.velocitymotors.creditcard.api.model.PaymentStatusResponse;
import com.velocitymotors.creditcard.model.entity.PaymentCard;
import com.velocitymotors.creditcard.repository.PaymentCardRepository;
import com.velocitymotors.creditcard.service.CreditCardValidationService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Service
public class CreditCardValidationServiceImpl implements CreditCardValidationService {

    private static final Logger logger = LoggerFactory.getLogger(CreditCardValidationServiceImpl.class);

    private final PaymentCardRepository paymentCardRepository;

    public CreditCardValidationServiceImpl(PaymentCardRepository paymentCardRepository) {
        this.paymentCardRepository = paymentCardRepository;
    }

    @Override
    public PaymentStatusResponse retrievePaymentStatus(String paymentReference) {
        if (paymentReference == null || paymentReference.isBlank()) {
            logger.warn("Rejected request with blank paymentReference");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "paymentReference is required");
        }

        PaymentCard paymentCard = paymentCardRepository.findByPaymentReference(paymentReference)
                .orElseThrow(() -> {
                    logger.warn("Payment not found for paymentReference={}", paymentReference);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Payment not found");
                });

        logger.info("Resolved paymentReference={} to status={}", paymentReference, paymentCard.getStatus());
        return new PaymentStatusResponse()
                .lastUpdateDate(OffsetDateTime.now(ZoneOffset.UTC))
                .status(paymentCard.getStatus());
    }
}

