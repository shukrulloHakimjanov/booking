package com.spring.booking.dto.response;

import com.spring.booking.dto.projections.IdFirstNameDto;
import com.spring.booking.dto.projections.IdKeyDto;

import java.time.LocalTime;
import java.util.List;

public record HotelResponse(
        Long id,
        IdFirstNameDto owner,
        CityRepsonse city,
        String name,
        String description,
        String address,
        Float rating,
        LocalTime checkInTime,
        LocalTime checkOutTime,
        List<IdKeyDto> attachments,
        List<AmenityResponse> amenities
) {
}
