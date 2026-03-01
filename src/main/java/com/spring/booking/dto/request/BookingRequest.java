package com.spring.booking.dto.request;

import com.spring.booking.constant.enums.BookingStatus;
import com.spring.booking.dto.response.GuestInfo;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record BookingRequest(

        @NotNull(message = "User ID cannot be null")
        @Min(value = 1, message = "User ID must be greater than 0")
        Long userId,

        @NotNull(message = "Hotel ID cannot be null")
        @Min(value = 1, message = "Hotel ID must be greater than 0")
        Long hotelId,

        @NotNull(message = "Room ID cannot be null")
        @Min(value = 1, message = "Room ID must be greater than 0")
        Long roomId,

        @NotNull(message = "Check-in date cannot be null")
        LocalDate checkInDate,

        @NotNull(message = "Check-out date cannot be null")
        LocalDate checkOutDate,

        @NotNull(message = "Number of guests cannot be null")
        @Min(value = 1, message = "There must be at least 1 guest")
        Integer numGuests,

        @NotNull(message = "Total price cannot be null")
        @DecimalMin(value = "0.0", inclusive = false, message = "Total price must be positive")
        BigDecimal totalPrice,

        String specialRequests,

        List<GuestInfo> guests

) {
}
