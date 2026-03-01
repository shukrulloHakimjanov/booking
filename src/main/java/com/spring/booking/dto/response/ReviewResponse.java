package com.spring.booking.dto.response;

import com.spring.booking.dto.projections.IdFirstNameDto;
import com.spring.booking.dto.projections.IdNameDto;

import java.util.UUID;

public record ReviewResponse(
        Long id,
        UUID bookingId,
        IdFirstNameDto user,
        IdNameDto hotel,
        Integer rating,
        Integer serviceRating,
        String comment
) {
}
