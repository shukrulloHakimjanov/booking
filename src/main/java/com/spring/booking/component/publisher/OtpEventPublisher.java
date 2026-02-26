package com.spring.booking.component.publisher;

import com.spring.booking.component.producer.OtpEventProducer;
import com.spring.booking.dto.payload.EmailOtpEventDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class OtpEventPublisher {

    private final OtpEventProducer otpEventProducer;

    public void sendVerificationCode(Long userId, String email) {
        sendEvent("SEND_VERIFICATION_CODE", userId, email, null);
    }


    private void sendEvent(String eventType, Long userId, String email, String code) {

        EmailOtpEventDto dto = new EmailOtpEventDto();
        dto.setEventType(eventType);
        dto.setTo(email);
        dto.setKey(eventType);
        dto.setUserId(userId);
        dto.setCode(code);
        dto.setCorrelationId(UUID.randomUUID().toString());

        otpEventProducer.sendMessage(dto);
    }
}