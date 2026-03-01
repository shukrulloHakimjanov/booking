package com.spring.booking.dto.request;


import com.spring.booking.constant.enums.Currency;
import com.spring.booking.constant.enums.PaymentMethod;

import java.math.BigDecimal;
import java.util.UUID;

public record PaymentRequest(
        UUID referenceId,
        BigDecimal amount,
        Currency currency,
        String senderName,
        String senderToken,
        PaymentMethod paymentMethod
) {
}