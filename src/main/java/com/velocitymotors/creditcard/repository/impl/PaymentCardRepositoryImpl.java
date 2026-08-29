package com.velocitymotors.creditcard.repository.impl;

import com.velocitymotors.creditcard.model.entity.PaymentCard;
import com.velocitymotors.creditcard.repository.PaymentCardJpaRepository;
import com.velocitymotors.creditcard.repository.PaymentCardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PaymentCardRepositoryImpl implements PaymentCardRepository {

    private final PaymentCardJpaRepository paymentCardJpaRepository;

    @Override
    public Optional<PaymentCard> findByPaymentReference(String paymentReference) {
        return paymentCardJpaRepository.findById(paymentReference);
    }

    @Override
    public PaymentCard save(PaymentCard paymentCard) {
        return paymentCardJpaRepository.save(paymentCard);
    }

    @Override
    public long count() {
        return paymentCardJpaRepository.count();
    }
}
