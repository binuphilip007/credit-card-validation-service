package com.velocitymotors.creditcard.repository.impl;

import com.velocitymotors.creditcard.api.model.PaymentStatusResponse;
import com.velocitymotors.creditcard.model.entity.PaymentCard;
import com.velocitymotors.creditcard.repository.PaymentCardJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PaymentCardRepositoryImplTest {

    @Mock
    private PaymentCardJpaRepository paymentCardJpaRepository;

    private PaymentCardRepositoryImpl repository;

    @BeforeEach
    void setUp() {
        repository = new PaymentCardRepositoryImpl(paymentCardJpaRepository);
    }

    @Test
    void findByPaymentReferenceDelegatesToJpaRepositoryFindById() {
        PaymentCard paymentCard = new PaymentCard("CC123456789", PaymentStatusResponse.StatusEnum.APPROVED);
        when(paymentCardJpaRepository.findById("CC123456789")).thenReturn(Optional.of(paymentCard));

        Optional<PaymentCard> result = repository.findByPaymentReference("CC123456789");

        assertThat(result).contains(paymentCard);
    }

    @Test
    void saveDelegatesToJpaRepositorySave() {
        PaymentCard paymentCard = new PaymentCard("CC987654321", PaymentStatusResponse.StatusEnum.REJECTED);
        when(paymentCardJpaRepository.save(paymentCard)).thenReturn(paymentCard);

        PaymentCard result = repository.save(paymentCard);

        assertThat(result).isSameAs(paymentCard);
        verify(paymentCardJpaRepository).save(paymentCard);
    }

    @Test
    void countDelegatesToJpaRepositoryCount() {
        when(paymentCardJpaRepository.count()).thenReturn(2L);

        assertThat(repository.count()).isEqualTo(2L);
    }
}
