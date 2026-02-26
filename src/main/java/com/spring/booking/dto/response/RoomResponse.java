package com.spring.booking.dto.response;

import com.spring.booking.constant.enums.AccommodationType;
import com.spring.booking.constant.enums.Status;

public record RoomResponse(

        Long id,
        Long roomTypeId,
        String roomNumber,
        Integer floor,
        Status status,
        AccommodationType accommodationType

) {
}
