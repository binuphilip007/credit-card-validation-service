package com.velocitymotors.creditcard.repository;

import com.velocitymotors.creditcard.model.entity.PaymentCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentCardJpaRepository extends JpaRepository<PaymentCard, String> {
}
