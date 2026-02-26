package com.spring.booking.controller;

import com.spring.booking.constant.enums.Status;
import com.spring.booking.dto.pageRequest.PageRequestDto;
import com.spring.booking.dto.request.RoomRequest;
import com.spring.booking.dto.response.RoomResponse;
import com.spring.booking.service.RoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/rooms")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Room API", description = "APIs for managing rooms")
public class RoomController {

    RoomService roomService;

    @Operation(summary = "Create room")
    @PostMapping
    public ResponseEntity<RoomResponse> create(@Valid @RequestBody RoomRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(roomService.create(request));
    }

    @Operation(summary = "Update room")
    @PutMapping("/{id}")
    public ResponseEntity<RoomResponse> update(@PathVariable Long id, @Valid @RequestBody RoomRequest request) {
        return ResponseEntity.ok(roomService.update(id, request));
    }

    @Operation(summary = "Get room by ID")
    @GetMapping("/{id}")
    public ResponseEntity<RoomResponse> get(@PathVariable Long id) {
        return ResponseEntity.ok(roomService.get(id));
    }

    @Operation(summary = "Search rooms with pagination")
    @GetMapping("/search")
    public ResponseEntity<Page<RoomResponse>> search(PageRequestDto pageRequestDtoParams) {
        return ResponseEntity.ok(roomService.getAllWithSearch(pageRequestDtoParams));
    }

    @Operation(summary = "Get rooms by room type")
    @GetMapping("/room-type/{roomTypeId}")
    public ResponseEntity<Page<RoomResponse>> getByRoomTypeId(@PathVariable Long roomTypeId, PageRequestDto pageRequestDtoParams) {
        return ResponseEntity.ok(roomService.getByRoomTypeId(roomTypeId, pageRequestDtoParams));
    }

    @Operation(summary = "Get rooms by hotel")
    @GetMapping("/hotel/{hotelId}")
    public ResponseEntity<Page<RoomResponse>> getByHotelId(@PathVariable Long hotelId, PageRequestDto pageRequestDtoParams) {
        return ResponseEntity.ok(roomService.getByHotelId(hotelId, pageRequestDtoParams));
    }

    @Operation(summary = "Get rooms by status")
    @GetMapping("/status/{status}")
    public ResponseEntity<Page<RoomResponse>> getByStatus(@PathVariable Status status, PageRequestDto pageRequestDtoParams) {
        return ResponseEntity.ok(roomService.getByStatus(status, pageRequestDtoParams));
    }

    @Operation(summary = "Get rooms by floor")
    @GetMapping("/floor/{floor}")
    public ResponseEntity<Page<RoomResponse>> getByFloor(@PathVariable Integer floor, PageRequestDto pageRequestDtoParams) {
        return ResponseEntity.ok(roomService.getByFloor(floor, pageRequestDtoParams));
    }

    @Operation(summary = "Get rooms by hotel and status")
    @GetMapping("/hotel/{hotelId}/status/{status}")
    public ResponseEntity<Page<RoomResponse>> getByHotelIdAndStatus(@PathVariable Long hotelId, @PathVariable Status status, @ModelAttribute PageRequestDto pageRequestDtoParams) {
        return ResponseEntity.ok(roomService.getByHotelIdAndStatus(hotelId, status, pageRequestDtoParams));
    }

    @Operation(summary = "Soft delete room")
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        roomService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Update room status")
    @PatchMapping("/{id}/status/{status}")
    public RoomResponse updateStatus(@PathVariable Long id, @PathVariable Status status) {
        return roomService.updateStatus(id, status);
    }
}