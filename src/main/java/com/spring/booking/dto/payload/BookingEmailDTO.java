package com.spring.booking.dto.payload;

import com.spring.booking.constant.enums.BookingStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Setter
@Getter
@Builder
public class BookingEmailDTO {

    private String email;
    private String customerName;
    private String bookingId;
    private String hotelName;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private Integer guests;
    private BigDecimal amount;
    private String key;
    private String correlationId;
    private BookingStatus status;
}
