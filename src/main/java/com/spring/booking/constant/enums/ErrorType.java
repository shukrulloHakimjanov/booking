package com.spring.booking.constant.enums;

public enum ErrorType {

    // 400
    VALIDATION,          // Invalid input, missing fields, wrong format
    BAD_REQUEST,        // Request is syntactically correct but logically wrong
    CLIENT_ERROR,
    // 401 / 403
    UNAUTHORIZED,       // User not authenticated
    FORBIDDEN,          // User authenticated but no permission
    // 404
    NOT_FOUND,          // Entity not found
    // 409
    CONFLICT,           // Duplicate data (e.g. CityAlreadyExists)
    // 422
    BUSINESS,           // Business rule violation
    // 500
    INTERNAL,           // Unexpected server error
    // 502 / 503
    EXTERNAL,           // External service failure


}
