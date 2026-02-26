package com.spring.booking.exception.exceptions404;


import com.spring.booking.constant.enums.ErrorType;
import com.spring.booking.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class PaymentNotfoundException extends ApplicationException {

    public PaymentNotfoundException(String message ) {
        super(40401, message, ErrorType.NOT_FOUND, HttpStatus.NOT_FOUND);
    }
}
