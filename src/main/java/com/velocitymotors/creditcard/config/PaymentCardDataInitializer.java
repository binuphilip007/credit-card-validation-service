package com.velocitymotors.creditcard.config;

import com.velocitymotors.creditcard.api.model.PaymentStatusResponse;
import com.velocitymotors.creditcard.model.entity.PaymentCard;
import com.velocitymotors.creditcard.repository.PaymentCardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentCardDataInitializer implements ApplicationRunner {

    private final PaymentCardRepository paymentCardRepository;

    @Override
    public void run(ApplicationArguments args) {
        if (paymentCardRepository.count() > 0) {
            log.debug("Skipping payment card seed data because records already exist");
            return;
        }

        paymentCardRepository.save(new PaymentCard("CC123456789", PaymentStatusResponse.StatusEnum.APPROVED));
        paymentCardRepository.save(new PaymentCard("CC987654321", PaymentStatusResponse.StatusEnum.REJECTED));
        log.debug("Seeded {} payment cards into the database", paymentCardRepository.count());
    }
}
