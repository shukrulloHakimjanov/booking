package com.spring.booking.controller;

import com.spring.booking.dto.pageRequest.PageRequestDto;
import com.spring.booking.dto.request.ReviewRequest;
import com.spring.booking.dto.response.ReviewResponse;
import com.spring.booking.service.ReviewService;
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
@RequestMapping("/api/v1/reviews")
@SecurityRequirement(name = "Bearer Authentication")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Reviews", description = "APIs for managing hotel reviews")
public class ReviewController {

    ReviewService reviewService;

    @Operation(summary = "Create review", description = "Creates a review for a completed booking")
    @PostMapping
    public ReviewResponse create(@RequestBody ReviewRequest request) {
        log.info("Creating review for booking: {}", request.bookingId());
        return reviewService.create(request);
    }

    @Operation(summary = "Get review by ID", description = "Returns review details by review ID")
    @GetMapping("/{id}")
    public ReviewResponse get(@PathVariable Long id) {
        log.info("Getting review with id: {}", id);
        return reviewService.get(id);
    }

    @Operation(summary = "Update review", description = "Updates an existing review by ID")
    @PutMapping("/{id}")
    public ReviewResponse update(@PathVariable Long id, @RequestBody ReviewRequest request) {
        log.info("Updating review with id: {}", id);
        return reviewService.update(id, request);
    }

    @Operation(summary = "Get all reviews", description = "Returns a paginated list of reviews using filter criteria")
    @GetMapping
    public Page<ReviewResponse> getAll(PageRequestDto pageRequestDto) {
        log.info("Getting all reviews with filter");
        return reviewService.getAll(pageRequestDto);
    }

    @Operation(summary = "Delete review", description = "Deletes a review by ID")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        log.info("Deleting review with id: {}", id);
        reviewService.delete(id);
    }

    @Operation(summary = "Get reviews by hotel", description = "Returns paginated reviews for a specific hotel")
    @GetMapping("/by-hotel/{hotelId}")
    public Page<ReviewResponse> getByHotelId(@PathVariable Long hotelId, @ModelAttribute PageRequestDto pageRequestDto) {
        log.info("Getting reviews for hotelId: {}", hotelId);
        return reviewService.getByHotelId(hotelId, pageRequestDto);
    }

    @Operation(summary = "Get reviews by user", description = "Returns paginated reviews for a specific user")
    @GetMapping("/by-user/{userId}")
    public Page<ReviewResponse> getByUserId(@PathVariable Long userId, @ModelAttribute PageRequestDto pageRequestDto) {
        log.info("Getting reviews for userId: {}", userId);
        return reviewService.getByUserId(userId, pageRequestDto);
    }

    @Operation(summary = "Get reviews by booking", description = "Returns paginated reviews for a specific booking")
    @GetMapping("/by-booking/{bookingId}")
    public Page<ReviewResponse> getByBookingId(@PathVariable Long bookingId, @ModelAttribute PageRequestDto pageRequestDto) {
        log.info("Getting reviews for bookingId: {}", bookingId);
        return reviewService.getByBookingId(bookingId, pageRequestDto);
    }

    @Operation(summary = "Get reviews by minimum rating", description = "Returns paginated reviews with rating >= minRating")
    @GetMapping("/by-min-rating/{minRating}")
    public Page<ReviewResponse> getByMinRating(@PathVariable Integer minRating, @ModelAttribute PageRequestDto pageRequestDto) {
        log.info("Getting reviews with minRating: {}", minRating);
        return reviewService.getByMinRating(minRating, pageRequestDto);
    }

    @Operation(summary = "Get reviews by hotel and minimum rating", description = "Returns paginated reviews for a hotel with rating >= minRating")
    @GetMapping("/by-hotel/{hotelId}/min-rating/{minRating}")
    public Page<ReviewResponse> getByHotelIdAndMinRating(@PathVariable Long hotelId, @PathVariable Integer minRating, @ModelAttribute PageRequestDto pageRequestDto) {
        log.info("Getting reviews for hotelId: {} with minRating: {}", hotelId, minRating);
        return reviewService.getByHotelIdAndMinRating(hotelId, minRating, pageRequestDto);
    }

}
