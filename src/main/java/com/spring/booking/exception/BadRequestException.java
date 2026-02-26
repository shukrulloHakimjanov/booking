package com.spring.booking.exception;

import com.spring.booking.constant.enums.ErrorType;
import org.springframework.http.HttpStatus;

public class BadRequestException extends ApplicationException {

    public BadRequestException(String message) {
        super(40001, message, ErrorType.BAD_REQUEST, HttpStatus.BAD_REQUEST);
    }
}
