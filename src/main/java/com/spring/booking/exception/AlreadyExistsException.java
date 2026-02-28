package com.spring.booking.exception;

import com.spring.booking.constant.enums.ErrorType;
import org.springframework.http.HttpStatus;

public class AlreadyExistsException extends ApplicationException{
    public AlreadyExistsException(String message) {
        super(40903, message, ErrorType.CLIENT_ERROR, HttpStatus.CONFLICT);
    }
}
