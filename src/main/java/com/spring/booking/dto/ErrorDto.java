package com.spring.booking.dto;

import com.spring.booking.constant.enums.ErrorType;
import lombok.Builder;

import java.util.List;

@Builder
public record ErrorDto(
        int code,
        String message,
        ErrorType type,
        List<String> validationErrors) {
}
