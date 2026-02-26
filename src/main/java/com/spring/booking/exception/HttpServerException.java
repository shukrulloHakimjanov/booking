package com.spring.booking.exception;

import com.spring.booking.constant.enums.ErrorType;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import static com.spring.booking.constant.enums.Error.INTERNAL_SERVICE_ERROR_CODE;

public class HttpServerException extends ApplicationException {

    public HttpServerException(String message, HttpStatusCode status) {
        super(INTERNAL_SERVICE_ERROR_CODE.getCode(), message, ErrorType.EXTERNAL, HttpStatus.valueOf(status.value()));
    }
}
