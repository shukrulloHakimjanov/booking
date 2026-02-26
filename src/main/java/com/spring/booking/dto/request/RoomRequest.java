package com.spring.booking.dto.request;

import com.spring.booking.constant.enums.AccommodationType;
import com.spring.booking.constant.enums.Status;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RoomRequest(

        @NotNull(message = "Room type ID cannot be null")
        @Min(value = 1, message = "Room type ID must be greater than 0")
        Long roomTypeId,

        @NotBlank(message = "Room number cannot be empty")
        @Size(max = 20, message = "Room number must not exceed 20 characters")
        String roomNumber,

        @NotNull(message = "Floor cannot be null")
        @Min(value = 0, message = "Floor must be 0 or higher")
        Integer floor,

        @NotNull(message = "Status cannot be null")
        Status status,

        AccommodationType accommodationType
) {
}
