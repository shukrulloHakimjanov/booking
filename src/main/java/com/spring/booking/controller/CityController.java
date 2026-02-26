package com.spring.booking.controller;

import com.spring.booking.dto.pageRequest.PageRequestDto;
import com.spring.booking.dto.request.CityRequest;
import com.spring.booking.dto.response.CityRepsonse;
import com.spring.booking.service.CityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@AllArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
@RequestMapping("/api/city")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Tag(name = "Cities", description = "APIs for managing cities")
@Validated
public class CityController {

    CityService cityService;

    @Operation(summary = "Create city", description = "Creates a new city")
    @PostMapping
    public CityRepsonse create(@Valid @RequestBody CityRequest request) {
        return cityService.create(request);
    }

    @Operation(summary = "Get city by ID", description = "Returns city details by city ID")
    @GetMapping("/{id}")
    public CityRepsonse get(@PathVariable Long id) {
        return cityService.get(id);
    }

    @Operation(summary = "Update city", description = "Updates an existing city by ID")
    @PutMapping("/{id}")
    public CityRepsonse update(@PathVariable Long id, @Valid @RequestBody CityRequest request) {
        return cityService.update(id, request);
    }

    @Operation(summary = "Delete city", description = "Deletes a city by ID")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        cityService.delete(id);
    }

    @Operation(summary = "Get all cities", description = "Returns a paginated list of cities using filter options")
    @GetMapping
    public Page<CityRepsonse> getAll(PageRequestDto pageRequestDto) {
        return cityService.getAll(pageRequestDto);
    }
}
