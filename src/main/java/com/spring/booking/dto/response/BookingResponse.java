package com.spring.booking.dto.response;

import com.spring.booking.constant.enums.BookingStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record BookingResponse(
        Long id,
        String bookingUuid,
        UserResponse user,
        HotelResponse hotel,
        RoomResponse room,
        LocalDate checkInDate,
        LocalDate checkOutDate,
        Integer numGuests,
        BigDecimal totalPrice,
        String currency,
        BookingStatus status,
        String specialRequests,
        String paymentId,
        List<GuestInfo> guests

) {
}
