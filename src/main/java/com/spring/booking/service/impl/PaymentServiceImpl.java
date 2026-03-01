package com.spring.booking.service.impl;

import com.spring.booking.constant.enums.BookingStatus;
import com.spring.booking.constant.enums.Currency;
import com.spring.booking.constant.enums.PaymentStatus;
import com.spring.booking.dto.request.PaymentRequest;
import com.spring.booking.dto.response.PaymentDTO;
import com.spring.booking.entity.Payment;
import com.spring.booking.exception.NotFoundException;
import com.spring.booking.mapper.PaymentMapper;
import com.spring.booking.repository.PaymentRepository;
import com.spring.booking.service.BookingService;
import com.spring.booking.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final BookingService bookingService;

    @Override
    public void createPayment(PaymentRequest request) {
        Payment payment = Payment.builder()
                .bookingId(request.referenceId())
                .amount(request.amount())
                .currency(Currency.USD)
                .paymentMethod(request.paymentMethod())
                .status(PaymentStatus.PENDING)
                .isActive(true)
                .build();
        paymentRepository.save(payment);
        bookingService.updateStatus(payment.getBookingId(), BookingStatus.BOOKING_PENDING_APPROVE_PAYMENT);
    }

    @Override
    public PaymentDTO getPaymentById(Long id) {
        return paymentMapper.toResponse(findPaymentById(id));
    }

    @Override
    public PaymentDTO updatePaymentStatus(Long id, PaymentStatus status) {
        Payment payment = findPaymentById(id);
        payment.setStatus(status);
        Payment updated = paymentRepository.save(payment);

        if (status == PaymentStatus.SUCCESS) {
            bookingService.updateStatusAndPaymentId(payment.getBookingId(), BookingStatus.BOOKING_CONFIRMED, String.valueOf(payment.getId()));
        }

        return paymentMapper.toResponse(updated);
    }

    @Override
    public void deletePayment(Long id) {
        Payment payment = findPaymentById(id);
        payment.setActive(false);
        paymentRepository.save(payment);
    }

    @Override
    public List<PaymentDTO> getAllPayments() {
        return paymentRepository.findAll()
                .stream()
                .filter(Payment::isActive)
                .map(paymentMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<PaymentDTO> getPaymentsByBookingId(Long bookingId) {
        return paymentRepository.findByBookingIdAndIsActiveTrue(bookingId)
                .stream()
                .map(paymentMapper::toResponse)
                .collect(Collectors.toList());
    }


    private Payment findPaymentById(Long id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Payment not found with id: " + id));
    }

}