package com.spring.booking.dto.payload;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EmailOtpEventDto {
    private String eventType;
    private String to;
    private Long userId;
    private String code;
    private Boolean isValid;
    private String key;
    private String correlationId;
}
