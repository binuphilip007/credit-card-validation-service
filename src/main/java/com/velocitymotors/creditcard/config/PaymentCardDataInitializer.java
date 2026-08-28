package com.velocitymotors.creditcard.config;

import com.velocitymotors.creditcard.api.model.PaymentStatusResponse;
import com.velocitymotors.creditcard.model.entity.PaymentCard;
import com.velocitymotors.creditcard.repository.PaymentCardRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class PaymentCardDataInitializer implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(PaymentCardDataInitializer.class);

    private final PaymentCardRepository paymentCardRepository;

    public PaymentCardDataInitializer(PaymentCardRepository paymentCardRepository) {
        this.paymentCardRepository = paymentCardRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        if (paymentCardRepository.count() > 0) {
            return;
        }

        paymentCardRepository.save(new PaymentCard("CC123456789", PaymentStatusResponse.StatusEnum.APPROVED));
        paymentCardRepository.save(new PaymentCard("CC987654321", PaymentStatusResponse.StatusEnum.REJECTED));
        logger.info("Seeded {} payment cards into the database", paymentCardRepository.count());
    }
}
