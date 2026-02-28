package com.spring.booking.exception;

import com.spring.booking.constant.enums.ErrorType;
import org.springframework.http.HttpStatus;

public class NotFoundException extends ApplicationException {
    public NotFoundException(String message) {
        super(40401, message, ErrorType.INTERNAL, HttpStatus.NOT_FOUND);
    }

}
