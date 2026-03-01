package com.spring.booking.dto.projections;

public record IdFloorAndNumber(
        Long id,
        String roomNumber,
        Integer floor
) {
}
