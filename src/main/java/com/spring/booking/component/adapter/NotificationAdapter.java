package com.spring.booking.component.adapter;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
@Slf4j
@Component
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class NotificationAdapter {

    RestClient restClient;

    public NotificationAdapter(@Qualifier("notificationRestClient") RestClient restClient) {
        this.restClient = restClient;
    }

    public Boolean verifyOtp(Long userId, String code) {

        Boolean isValid = restClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/auth/otp/verify")
                        .queryParam("userId", userId)
                        .queryParam("code", code)
                        .build())
                .retrieve()
                .body(Boolean.class);

        log.info("OTP verification result for userId {}: {}", userId, isValid);

        return isValid;
    }
}