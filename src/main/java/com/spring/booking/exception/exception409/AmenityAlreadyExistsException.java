package com.spring.booking.exception.exception409;

import com.spring.booking.constant.enums.ErrorType;
import com.spring.booking.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class AmenityAlreadyExistsException extends ApplicationException {
    public AmenityAlreadyExistsException(String message) {
        super(40901, message, ErrorType.CLIENT_ERROR, HttpStatus.CONFLICT);
    }
}
