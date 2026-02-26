package com.spring.booking.dto.response;

public record ReviewResponse(
        Long id,
        Long bookingId,
        Long userId,
        String userName,
        Long hotelId,
        String hotelName,
        Integer rating,
        Integer serviceRating,
        String comment
) {
}
