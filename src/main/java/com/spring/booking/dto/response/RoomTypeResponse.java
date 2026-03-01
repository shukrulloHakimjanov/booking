package com.spring.booking.dto.response;


import com.spring.booking.dto.projections.IdNameDto;

import java.util.Set;

public record RoomTypeResponse(
        Long id,
        IdNameDto hotel,
        String name,
        String description,
        Integer maxGuests,
        String bedConfiguration,
        Set<AmenityResponse> amenities
) {
}
