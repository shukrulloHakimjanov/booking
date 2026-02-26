package com.spring.booking.controller;

import com.spring.booking.component.adapter.BankAdapter;
import com.spring.booking.constant.enums.PaymentStatus;
import com.spring.booking.dto.request.PaymentRequest;
import com.spring.booking.dto.response.PaymentDTO;
import com.spring.booking.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
@Validated
@Slf4j
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Payment", description = "Payment management APIs")
public class PaymentController {

    private final PaymentService paymentService;
    private final BankAdapter bankAdapter;


    @Operation(summary = "Create a new payment via JBank")
    @PostMapping("/jbank")
    public ResponseEntity<String> createPaymentViaJBank(@RequestBody PaymentRequest request) {
        log.info("Creating payment for bookingId={} amount={}", request.bookingId(), request.amount());

        bankAdapter.createTransaction(request);
        paymentService.createPayment(request);

        return ResponseEntity.ok("Transaction created successfully in JBank");
    }

    @Operation(summary = "Get payment by ID")
    @GetMapping("/{id}")
    public ResponseEntity<PaymentDTO> getPayment(@PathVariable Long id) {
        PaymentDTO payment = paymentService.getPaymentById(id);
        return ResponseEntity.ok(payment);
    }

    @Operation(summary = "Update payment status")
    @PatchMapping("/{id}/status")
    public ResponseEntity<PaymentDTO> updatePaymentStatus(@PathVariable Long id,
                                                          @RequestParam PaymentStatus status) {
        log.info("Updating paymentId={} to status={}", id, status);
        PaymentDTO updated = paymentService.updatePaymentStatus(id, status);
        return ResponseEntity.ok(updated);
    }

    @Operation(summary = "Soft delete payment")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable Long id) {
        log.info("Soft deleting paymentId={}", id);
        paymentService.deletePayment(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get all payments")
    @GetMapping
    public ResponseEntity<List<PaymentDTO>> getAllPayments() {
        List<PaymentDTO> payments = paymentService.getAllPayments();
        return ResponseEntity.ok(payments);
    }

    @Operation(summary = "Get payments by booking ID")
    @GetMapping("/booking/{bookingId}")
    public ResponseEntity<List<PaymentDTO>> getPaymentsByBooking(@PathVariable Long bookingId) {
        List<PaymentDTO> payments = paymentService.getPaymentsByBookingId(bookingId);
        return ResponseEntity.ok(payments);
    }
}