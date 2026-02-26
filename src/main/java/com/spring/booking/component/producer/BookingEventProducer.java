package com.spring.booking.component.producer;

import com.spring.booking.configuration.props.KafkaProps;
import com.spring.booking.dto.payload.BookingEmailDTO;
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
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class BookingEventProducer {

    KafkaProps kafkaProps;
    KafkaTemplate<String, BookingEmailDTO> bookingTemplate;

    public BookingEventProducer(KafkaProps kafkaProps,
                                @Qualifier("bookingTopic")
                                KafkaTemplate<String, BookingEmailDTO> bookingTemplate) {
        this.kafkaProps = kafkaProps;
        this.bookingTemplate = bookingTemplate;
    }


    public void sendMessage(final BookingEmailDTO payload) {
        final Message<BookingEmailDTO> message = MessageBuilder
                .withPayload(payload)
                .setHeader(KafkaHeaders.TOPIC, kafkaProps.getBookingEventTopic())
                .setHeader(KafkaHeaders.KEY, payload.getKey())
                .setHeader(KafkaHeaders.CORRELATION_ID, payload.getCorrelationId())
                .build();
        bookingTemplate.send(message);
    }
}
