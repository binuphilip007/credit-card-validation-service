package com.velocitymotors.creditcard.model.entity;

import com.velocitymotors.creditcard.api.model.PaymentStatusResponse;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "payment_cards")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaymentCard {

    @Id
    private String paymentReference;

    @Enumerated(EnumType.STRING)
    private PaymentStatusResponse.StatusEnum status;

    public PaymentCard(String paymentReference, PaymentStatusResponse.StatusEnum status) {
        this.paymentReference = paymentReference;
        this.status = status;
    }
}
