package com.spring.booking.exception.exceptions404;

import com.spring.booking.constant.enums.ErrorType;
import com.spring.booking.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class BookingNotFoundException extends ApplicationException {
    public BookingNotFoundException(String message) {
        super(40402, message, ErrorType.INTERNAL, HttpStatus.NOT_FOUND);
    }

}
