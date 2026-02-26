package com.spring.booking.controller;

import com.spring.booking.dto.pageRequest.PageRequestDto;
import com.spring.booking.dto.request.AmenityRequest;
import com.spring.booking.dto.response.AmenityResponse;
import com.spring.booking.service.AmenityService;
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
@RequestMapping("/api/v1/amenities")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Amenities", description = "APIs for managing hotel amenities")
public class AmenityController {

    AmenityService amenityService;

    @Operation(summary = "Create amenity", description = "Creates a new amenity")
    @PostMapping
    public AmenityResponse create(@RequestBody AmenityRequest request) {
        log.info("Creating amenity: {}", request);
        return amenityService.create(request);
    }

    @Operation(summary = "Get amenity by ID", description = "Returns a single amenity by its ID")
    @GetMapping("/{id}")
    public AmenityResponse get(@PathVariable Long id) {
        log.info("Getting amenity by id: {}", id);
        return amenityService.get(id);
    }

    @Operation(summary = "Update amenity", description = "Updates an existing amenity by ID")
    @PutMapping("/{id}")
    public AmenityResponse update(@PathVariable Long id, @RequestBody AmenityRequest request) {
        log.info("Updating amenity id: {}", id);
        return amenityService.update(id, request);
    }

    @Operation(summary = "Get all amenities", description = "Returns a paginated list of amenities using filter options")
    @GetMapping
    public Page<AmenityResponse> getAll(PageRequestDto pageRequestDto) {
        log.info("Getting all amenities with filter: {}", pageRequestDto);
        return amenityService.getAll(pageRequestDto);
    }

    @Operation(summary = "Delete amenity", description = "Deletes an amenity by ID")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        log.info("Deleting amenity id: {}", id);
        amenityService.delete(id);
    }
}
