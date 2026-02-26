package com.spring.booking.dto.auth;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class VerifyCodeDTO {
    private String email;
    private String code;
}

