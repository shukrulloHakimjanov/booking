package com.spring.booking.dto.response;


import java.util.Set;

public record RoomTypeResponse(
        Long id,
        HotelResponse hotel,
        String name,
        String description,
        Integer maxGuests,
        String bedConfiguration,
        Set<AmenityResponse> amenities
) {}
