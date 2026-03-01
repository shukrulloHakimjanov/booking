package com.spring.booking.component.adapter;


import com.spring.booking.dto.payload.BankTransactionRequest;
import com.spring.booking.dto.request.PaymentRequest;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.UUID;

@Slf4j
@Component
@Setter
@Getter
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class BankAdapter {
    RestClient restClient;
    String receiverName;
    String receiverToken;
    String merchantId;

    public BankAdapter(@Qualifier("bankRestClient") RestClient restClient,
                       @Value("${bank.receiver-name}") String receiverName,
                       @Value("${bank.receiver-token}") String receiverToken,
                       @Value("${bank.merchant}") String merchantId) {
        this.restClient = restClient;
        this.receiverName = receiverName;
        this.receiverToken = receiverToken;
        this.merchantId = merchantId;
    }

    public void createTransaction(PaymentRequest request) {

        BankTransactionRequest bankRequest = new BankTransactionRequest();

        bankRequest.setReferenceId(request.referenceId());
        bankRequest.setAmount(request.amount());
        bankRequest.setSenderName(request.senderName());
        bankRequest.setSenderToken(request.senderToken());
        bankRequest.setCurrency(request.currency());

        bankRequest.setType("MERCHANT");
        bankRequest.setReceiverToken(receiverToken);
        bankRequest.setMerchantId(UUID.fromString(merchantId));
        bankRequest.setReceiverName(receiverName);

        restClient.post()
                .uri("/api/v1/transactions")
                .body(bankRequest)
                .retrieve()
                .toBodilessEntity();
        log.info("Transaction created successfully in J_BANK");
    }
}