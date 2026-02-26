package com.spring.booking.component.producer;

import com.spring.booking.configuration.props.KafkaProps;
import com.spring.booking.dto.payload.EmailOtpEventDto;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OtpEventProducer {
    KafkaTemplate<String, EmailOtpEventDto> emailOtpTemplate;
    KafkaProps kafkaProps;

    public OtpEventProducer(
            KafkaProps kafkaProps,
            @Qualifier("emailOtpEvents") KafkaTemplate<String, EmailOtpEventDto> emailOtpTemplate) {
        this.kafkaProps = kafkaProps;
        this.emailOtpTemplate = emailOtpTemplate;
    }

    public void sendMessage(final EmailOtpEventDto payload) {
        final Message<EmailOtpEventDto> message = MessageBuilder
                .withPayload(payload)
                .setHeader(KafkaHeaders.TOPIC, kafkaProps.getOtpEventTopic())
                .setHeader(KafkaHeaders.KEY, payload.getKey())
                .setHeader(KafkaHeaders.CORRELATION_ID, payload.getCorrelationId())
                .build();
        emailOtpTemplate.send(message);
    }

}
