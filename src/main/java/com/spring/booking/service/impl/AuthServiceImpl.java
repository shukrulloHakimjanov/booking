package com.spring.booking.service.impl;

import com.spring.booking.component.adapter.NotificationAdapter;
import com.spring.booking.component.publisher.OtpEventPublisher;
import com.spring.booking.configuration.jwt.JwtService;
import com.spring.booking.constant.enums.Role;
import com.spring.booking.constant.enums.UserStatus;
import com.spring.booking.dto.auth.LoginDTO;
import com.spring.booking.dto.auth.LoginResponseDTO;
import com.spring.booking.dto.auth.SignupDTO;
import com.spring.booking.dto.auth.UserAuth;
import com.spring.booking.entity.User;
import com.spring.booking.exception.BadRequestException;
import com.spring.booking.exception.UnauthorizedException;
import com.spring.booking.mapper.AuthMapper;
import com.spring.booking.repository.UserRepository;
import com.spring.booking.service.AuthService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final AuthMapper authMapper;
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final OtpEventPublisher otpEventPublisher;
    private final NotificationAdapter notificationAdapter;

    @Override
    @Transactional
    public void signup(SignupDTO request) {

        if (existsByEmail(request.getEmail())) {
            throw new BadRequestException("This email has already been used");
        }

        User user = authMapper.fromDto(request);

        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setFirstName(request.getFirstname());
        user.setLastName(request.getLastname());
        user.setStatus(UserStatus.PENDING_VERIFICATION);
        user.setRole(Role.USER);
        user.setPhone(request.getPhoneNumber());

        userRepo.save(user);

        otpEventPublisher.sendVerificationCode(user.getId(), user.getEmail());
    }

    @Override
    public void sendVerificationCode(String email) {

        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new BadRequestException("Email not found"));

        otpEventPublisher.sendVerificationCode(user.getId(), user.getEmail());
    }

    @Transactional
    @Override
    public void verifyCode(String email, String code) {

        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new BadRequestException("User not found"));

        boolean isValid = notificationAdapter.verifyOtp(user.getId(), code);

        log.info("Verify code is {}", isValid);

        if (!isValid) {
            throw new UnauthorizedException("Invalid or expired OTP");
        }

        user.setStatus(UserStatus.ACTIVE);
        userRepo.save(user);
    }

    @Override
    public LoginResponseDTO login(LoginDTO request) {

        User user = userRepo.findByEmail(request.getEmail())
                .orElseThrow(() -> new UnauthorizedException("Invalid credentials"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new UnauthorizedException("Invalid credentials");
        }

        if (!user.getStatus().equals(UserStatus.ACTIVE)) {
            throw new UnauthorizedException("Please verify your email before logging in.");
        }

        UserAuth userAuth = authMapper.toUserAuth(user);
        String jwtToken = jwtService.generateToken(userAuth);

        return LoginResponseDTO.builder()
                .token(jwtToken)
                .build();
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepo.existsByEmail(email);
    }
}