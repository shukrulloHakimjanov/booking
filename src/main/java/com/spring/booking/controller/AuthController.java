package com.spring.booking.controller;

import com.spring.booking.dto.auth.*;
import com.spring.booking.dto.response.MessageResponse;
import com.spring.booking.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Authentication", description = "Endpoints for user authentication and authorization")
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Validated
@Slf4j(topic = "Auth Endpoints")
public class AuthController {

    private final AuthService authService;

    @PostMapping(value = "/signup")
    @Operation(description = "Signup and send verification OTP")
    public ResponseEntity<MessageResponse> signup(@Valid @RequestBody SignupDTO request) {
        log.info("REST request to signup: {}", request);
        authService.signup(request);
        return ResponseEntity.ok(new MessageResponse("Signup successful, OTP sent to email"));
    }

    @PostMapping(value = "/verify")
    @Operation(description = "Verify OTP code sent to email")
    public ResponseEntity<MessageResponse> verifyCode(@Valid @RequestBody VerifyCodeDTO request) {
        log.info("REST request to verify OTP for email: {}", request.getEmail());
        authService.verifyCode(request.getEmail(), request.getCode());
        return ResponseEntity.ok(new MessageResponse("OTP verified successfully"));
    }

    @PostMapping(value = "/login")
    @Operation(description = "Login with email/password")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginDTO request) {
        log.info("REST request to login: {}", request);
        LoginResponseDTO loginResponse = authService.login(request);
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping(value = "/send-otp")
    @Operation(description = "Resend OTP to email")
    public ResponseEntity<MessageResponse> sendOtp(@Valid @RequestBody SendOtpDTO request) {
        log.info("REST request to resend OTP to email: {}", request.getEmail());
        authService.sendVerificationCode(request.getEmail());
        return ResponseEntity.ok(new MessageResponse("OTP sent successfully"));
    }
}
