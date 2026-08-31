package com.velocitymotors.creditcard.service.impl;

import com.velocitymotors.creditcard.api.model.PaymentStatusResponse;
import com.velocitymotors.creditcard.model.entity.PaymentCard;
import com.velocitymotors.creditcard.repository.PaymentCardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreditCardValidationServiceImplTest {

    @Mock
    private PaymentCardRepository paymentCardRepository;

    private CreditCardValidationServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new CreditCardValidationServiceImpl(paymentCardRepository);
    }

    @Test
    void returnsApprovedStatusWhenPaymentCardExists() {
        PaymentCard paymentCard = new PaymentCard("CC123456789", PaymentStatusResponse.StatusEnum.APPROVED);
        when(paymentCardRepository.findByPaymentReference("CC123456789")).thenReturn(Optional.of(paymentCard));

        PaymentStatusResponse response = service.retrievePaymentStatus("CC123456789");

        assertThat(response.getStatus()).isEqualTo(PaymentStatusResponse.StatusEnum.APPROVED);
        assertThat(response.getLastUpdateDate()).isNotNull();
    }

    @Test
    void returnsRejectedStatusWhenPaymentCardExists() {
        PaymentCard paymentCard = new PaymentCard("CC987654321", PaymentStatusResponse.StatusEnum.REJECTED);
        when(paymentCardRepository.findByPaymentReference("CC987654321")).thenReturn(Optional.of(paymentCard));

        PaymentStatusResponse response = service.retrievePaymentStatus("CC987654321");

        assertThat(response.getStatus()).isEqualTo(PaymentStatusResponse.StatusEnum.REJECTED);
    }

    @Test
    void throwsNotFoundWhenPaymentCardDoesNotExist() {
        when(paymentCardRepository.findByPaymentReference("UNKNOWN123")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.retrievePaymentStatus("UNKNOWN123"))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Payment not found");
    }

    @Test
    void throwsBadRequestWhenPaymentReferenceIsBlank() {
        assertThatThrownBy(() -> service.retrievePaymentStatus(" "))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("paymentReference is required");

        verify(paymentCardRepository, org.mockito.Mockito.never()).findByPaymentReference(org.mockito.ArgumentMatchers.anyString());
    }

    @Test
    void throwsBadRequestWhenPaymentReferenceIsNull() {
        assertThatThrownBy(() -> service.retrievePaymentStatus(null))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("paymentReference is required");
    }
}
