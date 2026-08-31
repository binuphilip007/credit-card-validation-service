package com.velocitymotors.creditcard.controller;

import com.velocitymotors.creditcard.api.model.PaymentStatusResponse;
import com.velocitymotors.creditcard.api.model.PaymentStatusRetrievalRequest;
import com.velocitymotors.creditcard.service.CreditCardValidationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.OffsetDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreditCardValidationControllerUnitTest {

    @Mock
    private CreditCardValidationService creditCardValidationService;

    private CreditCardValidationController controller;

    @BeforeEach
    void setUp() {
        controller = new CreditCardValidationController(creditCardValidationService);
    }

    @Test
    void delegatesToServiceAndReturnsOkResponse() {
        PaymentStatusResponse serviceResponse = new PaymentStatusResponse()
                .status(PaymentStatusResponse.StatusEnum.APPROVED)
                .lastUpdateDate(OffsetDateTime.now());
        when(creditCardValidationService.retrievePaymentStatus("CC123456789")).thenReturn(serviceResponse);

        ResponseEntity<PaymentStatusResponse> response = controller.retrievePaymentStatus(
                new PaymentStatusRetrievalRequest().paymentReference("CC123456789"));

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isSameAs(serviceResponse);
        verify(creditCardValidationService).retrievePaymentStatus("CC123456789");
    }
}
