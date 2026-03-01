package com.spring.booking.dto.request;

import jakarta.validation.constraints.*;

import java.util.Set;

public record RoomTypeRequest(

        @NotNull(message = "Hotel ID cannot be null")
        Long hotelId,

        @NotBlank(message = "Room type name cannot be empty")
        @Size(max = 100, message = "Room type name must not exceed 100 characters")
        String name,

        @Size(max = 1000, message = "Description must not exceed 1000 characters")
        String description,

        @NotNull(message = "Max guests is required")
        @Min(value = 1, message = "Max guests must be at least 1")
        @Max(value = 20, message = "Max guests cannot exceed 20")
        Integer maxGuests,

        @NotBlank(message = "Bed configuration cannot be empty")
        @Size(max = 500, message = "Bed configuration must not exceed 500 characters")
        String bedConfiguration,

        @NotEmpty(message = "Amenities list cannot be empty")
        @NotNull(message = "Amenity ID cannot be null")
        Set<Long> amenitiesIds
) {
}
