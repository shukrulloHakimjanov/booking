package com.spring.booking.dto.response;

import com.spring.booking.constant.enums.PaymentMethod;
import com.spring.booking.constant.enums.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PaymentDTO(
        Long id,
        Long bookingId,
        BigDecimal amount,
        String currency,
        PaymentMethod paymentMethod,
        PaymentStatus status,
        String transactionId,
        LocalDateTime createdAt,
        boolean isActive
) {
}
