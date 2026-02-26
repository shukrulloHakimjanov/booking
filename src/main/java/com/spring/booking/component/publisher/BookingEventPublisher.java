package com.spring.booking.component.publisher;

import com.spring.booking.component.producer.BookingEventProducer;
import com.spring.booking.dto.payload.BookingEmailDTO;
import com.spring.booking.dto.response.BookingResponse;
import com.spring.booking.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class BookingEventPublisher {

    private final BookingEventProducer bookingEventProducer;

    public void sendBookingEvent(BookingResponse response) {

        validate(response);

        BookingEmailDTO dto = BookingEmailDTO.builder()
                .bookingId(String.valueOf(response.id()))
                .email(response.user().email())
                .customerName(response.user().firstName())
                .hotelName(response.hotel().name())
                .checkInDate(response.checkInDate())
                .checkOutDate(response.checkOutDate())
                .status(response.status())
                .guests(response.guests() != null ? response.guests().size() : 0)
                .amount(response.totalPrice())
                .key("BOOKING_EVENT")
                .correlationId(UUID.randomUUID().toString())
                .build();

        bookingEventProducer.sendMessage(dto);
    }

    private void validate(BookingResponse response) {

        if (response.user() == null || response.user().email() == null) {
            throw new BadRequestException("User email is required");
        }

        if (response.hotel() == null || response.hotel().name() == null) {
            throw new BadRequestException("Hotel name is required");
        }

        if (response.checkInDate() == null || response.checkOutDate() == null) {
            throw new BadRequestException("Check-in and Check-out dates are required");
        }

        if (response.totalPrice() == null || response.totalPrice().doubleValue() <= 0) {
            throw new BadRequestException("Total price must be greater than 0");
        }

        if (response.status() == null) {
            throw new BadRequestException("Status is required");
        }
    }
}