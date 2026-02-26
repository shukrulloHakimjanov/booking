package com.spring.booking.dto.request;

import com.spring.booking.constant.enums.PaymentStatus;

import java.math.BigDecimal;

public record PaymentUpdateRequest(
        BigDecimal amount,
        PaymentStatus status
) {}
