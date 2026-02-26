package com.spring.booking.configuration.kafka;

import com.spring.booking.configuration.props.KafkaProps;
import com.spring.booking.dto.payload.BookingEmailDTO;
import com.spring.booking.dto.payload.DlqDto;
import com.spring.booking.dto.payload.EmailOtpEventDto;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.serializer.JacksonJsonSerializer;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@EnableConfigurationProperties(KafkaProps.class)  // <-- ensures KafkaProps bean is available
public class ProducerConfiguration {

    KafkaProps kafkaProps;

    private Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();

        if (kafkaProps.getBootstrapServers() != null)
            props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProps.getBootstrapServers());

        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JacksonJsonSerializer.class);

        if (kafkaProps.getClientId() != null)
            props.put(ProducerConfig.CLIENT_ID_CONFIG, kafkaProps.getClientId() + "-" + UUID.randomUUID());

        if (kafkaProps.getClientDnsLookup() != null)
            props.put(ProducerConfig.CLIENT_DNS_LOOKUP_CONFIG, kafkaProps.getClientDnsLookup());

        if (kafkaProps.getAcksConfig() != null) props.put(ProducerConfig.ACKS_CONFIG, kafkaProps.getAcksConfig());

        if (kafkaProps.getRetriesConfig() != null)
            props.put(ProducerConfig.RETRIES_CONFIG, kafkaProps.getRetriesConfig());

        props.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);

        props.put(ProducerConfig.BATCH_SIZE_CONFIG, kafkaProps.getBatchSizeConfig());
        props.put(ProducerConfig.LINGER_MS_CONFIG, kafkaProps.getLingerMsConfig());
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, kafkaProps.getBufferMemoryConfig());

        return props;
    }

    @Bean("dlqTopic")
    public KafkaTemplate<String, DlqDto> dlqTemplate() {
        return new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(producerConfigs()));
    }

    @Bean("bookingTopic")
    public KafkaTemplate<String, BookingEmailDTO> bookingTemplate() {
        return new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(producerConfigs()));
    }

    @Bean("emailOtpEvents")
    public KafkaTemplate<String, EmailOtpEventDto> emailOtpTemplate() {
        return new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(producerConfigs()));
    }
}
