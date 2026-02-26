package com.spring.booking.exception.exceptions404;

import com.spring.booking.constant.enums.ErrorType;
import com.spring.booking.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class RoomTypeNotFoundException extends ApplicationException {
    public RoomTypeNotFoundException(String message) {
        super(40407, message, ErrorType.INTERNAL, HttpStatus.NOT_FOUND);
    }

}
