package com.spring.booking.controller;

import com.spring.booking.constant.enums.BookingStatus;
import com.spring.booking.dto.pageRequest.PageRequestDto;
import com.spring.booking.dto.request.BookingRequest;
import com.spring.booking.dto.response.BookingResponse;
import com.spring.booking.service.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/bookings")
@SecurityRequirement(name = "Bearer Authentication")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Bookings", description = "APIs for managing bookings")
public class BookingController {

    BookingService bookingService;

    @Operation(summary = "Create booking", description = "Creates a new booking for a user")
    @PostMapping
    public BookingResponse create(@RequestBody BookingRequest request) {
        log.info("Creating booking for user: {}", request.userId());
        return bookingService.create(request);

    }

    @Operation(summary = "Get booking by ID", description = "Returns booking details by booking ID")
    @GetMapping("/{id}")
    public BookingResponse get(@PathVariable Long id) {
        log.info("Getting booking with id: {}", id);
        return bookingService.get(id);
    }

    @Operation(summary = "Update booking", description = "Updates an existing booking by ID")
    @PutMapping("/{id}")
    public BookingResponse update(@PathVariable Long id, @RequestBody BookingRequest request) {
        log.info("Updating booking with id: {}", id);
        return bookingService.update(id, request);
    }

    @Operation(summary = "Update booking status", description = "Updates only the status of a booking and sends Kafka event")
    @PatchMapping("/{id}/status")
    public BookingResponse updateStatus(@PathVariable Long id, @RequestParam BookingStatus status) {
        log.info("Updating booking status. id={}, newStatus={}", id, status);
        return bookingService.updateStatus(id, status);
    }

    @Operation(summary = "Get all bookings", description = "Returns a paginated list of bookings using filter criteria")
    @GetMapping
    public Page<BookingResponse> getAll(PageRequestDto pageRequestDto) {
        log.info("Getting all bookings with filter");
        return bookingService.getAll(pageRequestDto);
    }

    @Operation(summary = "Delete booking", description = "Deletes a booking by ID")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        log.info("Deleting booking with id: {}", id);
        bookingService.delete(id);
    }

    @Operation(summary = "Get bookings by user")
    @GetMapping("/user/{userId}")
    public Page<BookingResponse> getByUser(@PathVariable Long userId, PageRequestDto pageRequestDto) {
        log.info("Get bookings by user: {}", userId);
        return bookingService.getByUser(userId, pageRequestDto);
    }

    @Operation(summary = "Get bookings by hotel")
    @GetMapping("/hotel/{hotelId}")
    public Page<BookingResponse> getByHotel(@PathVariable Long hotelId, PageRequestDto pageRequestDto) {
        log.info("Get bookings by hotel: {}", hotelId);
        return bookingService.getByHotel(hotelId, pageRequestDto);
    }


    @Operation(summary = "Get bookings by status")
    @GetMapping("/status/{status}")
    public Page<BookingResponse> getByStatus(@PathVariable BookingStatus status, PageRequestDto pageRequestDto) {
        log.info("Get bookings by status: {}", status);
        return bookingService.getByStatus(status, pageRequestDto);
    }


}
