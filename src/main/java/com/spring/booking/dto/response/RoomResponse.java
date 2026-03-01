package com.spring.booking.dto.response;

import com.spring.booking.constant.enums.AccommodationType;
import com.spring.booking.constant.enums.Status;
import com.spring.booking.dto.projections.IdNameDto;

public record RoomResponse(

        Long id,
        IdNameDto roomType,
        String roomNumber,
        Integer floor,
        Status status,
        AccommodationType accommodationType

) {
}
