package com.spring.booking.service;

import com.spring.booking.dto.auth.LoginDTO;
import com.spring.booking.dto.auth.LoginResponseDTO;
import com.spring.booking.dto.auth.SignupDTO;

public interface AuthService {

    void signup(SignupDTO request);

    void sendVerificationCode(String email);

    void verifyCode(String email, String code);

    LoginResponseDTO login(LoginDTO request);

    boolean existsByEmail(String email);
}