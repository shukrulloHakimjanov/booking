package com.spring.booking.exception.exceptions404;

import com.spring.booking.constant.enums.ErrorType;
import com.spring.booking.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class UserNotFoundException extends ApplicationException {

    public UserNotFoundException(String message) {
        super(40409, message, ErrorType.INTERNAL, HttpStatus.NOT_FOUND);
    }

}
