package com.spring.booking.controller;

import com.spring.booking.component.producer.BookingEventProducer;
import com.spring.booking.component.producer.OtpEventProducer;
import com.spring.booking.dto.payload.BookingEmailDTO;
import com.spring.booking.dto.payload.EmailOtpEventDto;
import com.spring.booking.dto.response.MessageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Kafka Message API", description = "Endpoints for producing Kafka events such as booking, payment, and OTP messages")
@Slf4j
@RestController
@RequestMapping("/api/messages")
@SecurityRequirement(name = "Bearer Authentication")
@RequiredArgsConstructor
public class MessageController {

    private final BookingEventProducer bookingProducer;
    private final OtpEventProducer otpProducer;

    @Operation(summary = "Send booking event", description = "Produces a booking event message")
    @PostMapping("/booking")
    public ResponseEntity<MessageResponse> sendBookingEvent(@RequestBody BookingEmailDTO request) {
        log.info("Sending booking event: {}", request);
        bookingProducer.sendMessage(request);
        return ResponseEntity.ok(new MessageResponse("Booking event sent successfully"));
    }

    @Operation(summary = "Send OTP event", description = "Produces an OTP event message")
    @PostMapping("/otp")
    public ResponseEntity<MessageResponse> sendOtpCode(@RequestBody EmailOtpEventDto request) {
        log.info("Sending otp event: {}", request);
        otpProducer.sendMessage(request);
        return ResponseEntity.ok(new MessageResponse("Otp event sent successfully"));
    }
}