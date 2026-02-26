package com.spring.booking.exception.exceptions404;

import com.spring.booking.constant.enums.ErrorType;
import com.spring.booking.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class RoomNotFoundException extends ApplicationException {
    public RoomNotFoundException(String message) {
        super(40406, message, ErrorType.INTERNAL, HttpStatus.NOT_FOUND);
    }
}
