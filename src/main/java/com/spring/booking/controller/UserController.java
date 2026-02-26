package com.spring.booking.controller;

import com.spring.booking.constant.enums.Role;
import com.spring.booking.dto.pageRequest.PageRequestDto;
import com.spring.booking.dto.request.UserRequest;
import com.spring.booking.dto.response.UserResponse;
import com.spring.booking.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLDataException;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/users")
@SecurityRequirement(name = "Bearer Authentication")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Tag(name = "Users", description = "APIs for managing users")
public class UserController {

    UserService userService;

    @Operation(summary = "Create user", description = "Creates a new user")
    @PostMapping
    public UserResponse create(@Valid @RequestBody UserRequest request) {
        return userService.create(request);
    }

    @Operation(summary = "Get user by ID", description = "Returns user details by user ID")
    @GetMapping("/{id}")
    public UserResponse get(@PathVariable Long id) {
        return userService.get(id);
    }

    @Operation(summary = "Update user", description = "Updates an existing user by ID")
    @PutMapping("/{id}")
    public UserResponse update(@PathVariable Long id, @Valid @RequestBody UserRequest request) throws SQLDataException {
        return userService.update(id, request);
    }

    @Operation(summary = "Delete user", description = "Deletes a user by ID")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }

    @Operation(summary = "Get all users", description = "Returns a paginated list of users using filter criteria")
    @GetMapping
    public Page<UserResponse> getAll(PageRequestDto pageRequestDto) {
        return userService.getAll(pageRequestDto);

    }

    @Operation(summary = "Get users by role")
    @GetMapping("/by-role")
    public Page<UserResponse> getByRole(@RequestParam Role role, @ModelAttribute PageRequestDto pageRequestDtoParams) {
        log.info("Fetching users with role={}", role);
        return userService.getByRole(role, pageRequestDtoParams);
    }
}
