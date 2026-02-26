package com.spring.booking.controller;

import com.spring.booking.dto.pageRequest.PageRequestDto;
import com.spring.booking.dto.request.RoomTypeRequest;
import com.spring.booking.dto.response.RoomTypeResponse;
import com.spring.booking.service.RoomTypeService;
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
@RequestMapping("/api/v1/room-types")
@SecurityRequirement(name = "Bearer Authentication")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Room Types", description = "APIs for managing room types")
public class RoomTypeController {

    RoomTypeService roomTypeService;

    @Operation(summary = "Create room type", description = "Creates a new room type")
    @PostMapping
    public RoomTypeResponse create(@RequestBody RoomTypeRequest request) {
        log.info("Creating room type: {}", request.name());
        return roomTypeService.create(request);
    }

    @Operation(summary = "Get room type by ID", description = "Returns room type details by ID")
    @GetMapping("/{id}")
    public RoomTypeResponse get(@PathVariable Long id) {
        log.info("Getting room type with id: {}", id);
        return roomTypeService.get(id);
    }

    @Operation(summary = "Update room type", description = "Updates an existing room type by ID")
    @PutMapping("/{id}")
    public RoomTypeResponse update(@PathVariable Long id, @RequestBody RoomTypeRequest request) {
        log.info("Updating room type with id: {}", id);
        return roomTypeService.update(id, request);
    }

    @Operation(summary = "Get all room types", description = "Returns a paginated list of room types using filter criteria")
    @GetMapping
    public Page<RoomTypeResponse> getAll(PageRequestDto pageRequestDto) {
        log.info("Getting all room types with filter");
        return roomTypeService.getAll(pageRequestDto);
    }

    @Operation(summary = "Delete room type", description = "Deletes a room type by ID")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        log.info("Deleting room type with id: {}", id);
        roomTypeService.delete(id);
    }

    @Operation(summary = "Get room types by hotel")
    @GetMapping("/by-hotel/{hotelId}")
    public Page<RoomTypeResponse> getByHotel(@PathVariable Long hotelId, @ModelAttribute PageRequestDto pageRequestDtoParams) {

        log.info("Fetching room types for hotelId={}", hotelId);
        return roomTypeService.getByHotel(hotelId, pageRequestDtoParams);
    }

    @Operation(summary = "Get room types by minimum guests")
    @GetMapping("/by-min-guests")
    public Page<RoomTypeResponse> getByMinGuests(@RequestParam Integer minGuests, @ModelAttribute PageRequestDto pageRequestDtoParams) {

        log.info("Fetching room types with minGuests={}", minGuests);
        return roomTypeService.getByMinGuests(minGuests, pageRequestDtoParams);
    }

    @Operation(summary = "Get room types by hotel and minimum guests")
    @GetMapping("/by-hotel-and-min-guests")
    public Page<RoomTypeResponse> getByHotelAndMinGuests(@RequestParam Long hotelId, @RequestParam Integer minGuests, @ModelAttribute PageRequestDto pageRequestDtoParams) {

        log.info("Fetching room types for hotelId={} and minGuests={}", hotelId, minGuests);
        return roomTypeService.getByHotelAndMinGuests(hotelId, minGuests, pageRequestDtoParams);
    }


}
