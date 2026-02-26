package com.spring.booking.configuration.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "kafka")
@Data
public class KafkaProps {

    private String bookingEventTopic = "BOOKING_EVENT";
    private String otpEventTopic = "OTP_EVENT";

    private String bootstrapServers = "localhost:9092";
    private String clientId = "test_client";
    private String clientDnsLookup = "use_all_dns_ips";
    private String acksConfig = "all";
    private Integer retriesConfig = 3;
    private Integer batchSizeConfig = 16384;
    private Long lingerMsConfig = 100L;
    private Integer bufferMemoryConfig = 32768;
    private String groupId = "test_group";
    private String autoOffsetResetConfig = "latest";
}
