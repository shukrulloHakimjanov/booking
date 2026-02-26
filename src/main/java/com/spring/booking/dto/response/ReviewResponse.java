package com.spring.booking.dto.response;

import java.util.UUID;

public record ReviewResponse(
        Long id,
        UUID bookingId,
        Long userId,
        Long hotelId,
        Integer rating,
        Integer serviceRating,
        String comment
) {
}
