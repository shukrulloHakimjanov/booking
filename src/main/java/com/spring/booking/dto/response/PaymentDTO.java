package com.spring.booking.dto.response;

import com.spring.booking.constant.enums.PaymentMethod;
import com.spring.booking.constant.enums.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record PaymentDTO(
        Long id,
        UUID bookingId,
        BigDecimal amount,
        String currency,
        PaymentMethod paymentMethod,
        PaymentStatus status,
        String transactionId,
        LocalDateTime createdAt,
        boolean isActive
) {
}
