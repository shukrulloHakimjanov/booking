package com.spring.booking.dto.payload;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Setter
@Getter
public class BankTransactionRequest {

    private UUID referenceId;
    private String type;
    private BigDecimal amount;
    private String currency;
    private UUID merchantId;
    private String senderName;
    private String senderToken;
    private String receiverName;
    private String receiverToken;

}