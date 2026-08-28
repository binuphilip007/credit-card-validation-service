package com.velocitymotors.creditcard.controller;

import com.velocitymotors.creditcard.CreditCardValidationApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = CreditCardValidationApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class CreditCardValidationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void returnsApprovedStatusForKnownPaymentReference() throws Exception {
        mockMvc.perform(post("/host/credit-card-payment-api/payment-status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"paymentReference\":\"CC123456789\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("APPROVED"))
                .andExpect(jsonPath("$.lastUpdateDate").isString());
    }

    @Test
    void returnsRejectedStatusForKnownPaymentReference() throws Exception {
        mockMvc.perform(post("/host/credit-card-payment-api/payment-status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"paymentReference\":\"CC987654321\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("REJECTED"));
    }

    @Test
    void returnsNotFoundForUnknownPaymentReference() throws Exception {
        mockMvc.perform(post("/host/credit-card-payment-api/payment-status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"paymentReference\":\"UNKNOWN123\"}"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Payment not found"));
    }

    @Test
    void returnsBadRequestForBlankPaymentReference() throws Exception {
        mockMvc.perform(post("/host/credit-card-payment-api/payment-status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"paymentReference\":\" \"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("paymentReference is required"));
    }
}