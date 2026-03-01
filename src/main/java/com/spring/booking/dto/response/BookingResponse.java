package com.spring.booking.dto.response;

import com.spring.booking.constant.enums.BookingStatus;
import com.spring.booking.dto.projections.IdFirstNameDto;
import com.spring.booking.dto.projections.IdFloorAndNumber;
import com.spring.booking.dto.projections.IdNameDto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record BookingResponse(
        UUID id,
        IdFirstNameDto user,
        IdNameDto hotel,
        IdFloorAndNumber room,
        LocalDate checkInDate,
        LocalDate checkOutDate,
        Integer numGuests,
        BigDecimal totalPrice,
        BookingStatus status,
        String specialRequests,
        String paymentId,
        List<GuestInfo> guests

) {
}
