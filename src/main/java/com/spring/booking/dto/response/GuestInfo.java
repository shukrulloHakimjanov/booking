package com.spring.booking.dto.response;

public record GuestInfo(
        String fullName,
        Integer age,
        String passportNumber,
        String email,
        String phone
) {
}