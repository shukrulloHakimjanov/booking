package com.spring.booking.dto.response;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;

public record HotelResponse(
        Long id,
        UserResponse owner,
        CityRepsonse city,
        String name,
        String description,
        String address,
        BigDecimal rating,
        LocalTime checkInTime,
        LocalTime checkOutTime,
        List<AttachmentDto> attachments,
        List<AmenityResponse> amenities
) {
}
