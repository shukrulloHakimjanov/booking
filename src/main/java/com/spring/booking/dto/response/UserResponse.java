package com.spring.booking.dto.response;

import com.spring.booking.constant.enums.Role;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserResponse(

        Long id,
        String email,
        String firstName,
        String lastName,
        String phone,
        Role role,
        boolean active,
        LocalDateTime createdAt
) {
}
