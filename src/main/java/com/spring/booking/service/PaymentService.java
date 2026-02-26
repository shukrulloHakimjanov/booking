package com.spring.booking.service;

import com.spring.booking.constant.enums.PaymentStatus;
import com.spring.booking.dto.request.PaymentRequest;
import com.spring.booking.dto.response.PaymentDTO;

import java.util.List;

public interface PaymentService {


    void createPayment(PaymentRequest request);

    PaymentDTO getPaymentById(Long id);

    PaymentDTO updatePaymentStatus(Long id, PaymentStatus status);

    void deletePayment(Long id);

    List<PaymentDTO> getAllPayments();

    List<PaymentDTO> getPaymentsByBookingId(Long bookingId);
}
