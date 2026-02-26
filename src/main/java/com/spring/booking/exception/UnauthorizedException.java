package com.spring.booking.exception;

import com.spring.booking.constant.enums.ErrorType;
import org.springframework.http.HttpStatus;

public class UnauthorizedException extends ApplicationException {
    public UnauthorizedException(String message) {
        super(40301, message, ErrorType.UNAUTHORIZED, HttpStatus.UNAUTHORIZED);
    }
}
