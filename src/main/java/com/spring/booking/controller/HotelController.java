package com.spring.booking.controller;

import com.spring.booking.dto.pageRequest.HotelFilterDto;
import com.spring.booking.dto.request.HotelRequest;
import com.spring.booking.dto.response.HotelResponse;
import com.spring.booking.service.HotelService;
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
@RequestMapping("/api/v1/hotels")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Hotels", description = "APIs for managing hotels")
public class HotelController {

    HotelService hotelService;

    @Operation(summary = "Create hotel", description = "Creates a new hotel")
    @PostMapping
    public HotelResponse create(@RequestBody HotelRequest request) {
        log.info("Creating hotel: {}", request.name());
        return hotelService.create(request);
    }

    @Operation(summary = "Get hotel by ID", description = "Returns hotel details by hotel ID")
    @GetMapping("/{id}")
    public HotelResponse get(@PathVariable Long id) {
        log.info("Getting hotel with id: {}", id);
        return hotelService.get(id);
    }

    @Operation(summary = "Update hotel", description = "Updates an existing hotel by ID")
    @PutMapping("/{id}")
    public HotelResponse update(@PathVariable Long id, @RequestBody HotelRequest request) {
        log.info("Updating hotel with id: {}", id);
        return hotelService.update(id, request);
    }

    @Operation(summary = "Get all hotels", description = "Returns a paginated list of hotels using filter criteria")
    @GetMapping
    public Page<HotelResponse> getAll(HotelFilterDto filter) {
        log.info("Getting all hotels with filter");
        return hotelService.getAll(filter);
    }

    @Operation(summary = "Delete hotel", description = "Deletes a hotel by ID")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        log.info("Deleting hotel with id: {}", id);
        hotelService.delete(id);
    }
}
