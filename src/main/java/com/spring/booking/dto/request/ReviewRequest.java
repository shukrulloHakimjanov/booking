package com.spring.booking.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record ReviewRequest(

        @NotNull(message = "Booking ID cannot be null")
        @Min(value = 1, message = "Booking ID must be greater than 0")
        UUID bookingId,

        @NotNull(message = "User ID cannot be null")
        @Min(value = 1, message = "User ID must be greater than 0")
        Long userId,

        @NotNull(message = "Hotel ID cannot be null")
        @Min(value = 1, message = "Hotel ID must be greater than 0")
        Long hotelId,

        @NotNull(message = "Rating cannot be null")
        @Min(value = 1, message = "Rating must be at least 1")
        @Max(value = 5, message = "Rating cannot be more than 5")
        Integer rating,

        @Min(value = 1, message = "Service rating must be at least 1")
        @Max(value = 5, message = "Service rating cannot be more than 5")
        Integer serviceRating,

        @Size(max = 1000, message = "Comment must not exceed 1000 characters")
        String comment
) {
}
