package com.spring.booking.dto;

import lombok.Builder;
import com.spring.booking.constant.enums.ErrorType;

import java.util.List;

@Builder
public record ErrorDto(
        int code,
        String message,
        ErrorType type,
        List<String> validationErrors) {}
