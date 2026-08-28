package com.velocitymotors.creditcard.repository;

import com.velocitymotors.creditcard.model.entity.PaymentCard;

import java.util.Optional;

public interface PaymentCardRepository {

    Optional<PaymentCard> findByPaymentReference(String paymentReference);

    PaymentCard save(PaymentCard paymentCard);

    long count();
}
