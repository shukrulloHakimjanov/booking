package com.spring.booking.dto.request;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.Set;

public record HotelRequest(
        Long ownerId,
        Long cityId,
        String name,
        String description,
        String address,
        BigDecimal rating,
        LocalTime checkInTime,
        LocalTime checkOutTime,
        Set<Long> attachmentIds,
        Set<Long> amenitiesIds
) {}
