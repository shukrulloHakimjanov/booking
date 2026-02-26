package com.spring.booking.dto.request;


import com.spring.booking.constant.enums.PaymentMethod;

import java.math.BigDecimal;

public record PaymentRequest(
        String referenceId,
        Long bookingId,
        BigDecimal amount,
        String currency,
        String senderName,
        String senderToken,
        PaymentMethod paymentMethod
) {}