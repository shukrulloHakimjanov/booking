package com.spring.booking.service;

import com.spring.booking.constant.enums.BookingStatus;
import com.spring.booking.dto.pageRequest.PageRequestDto;
import com.spring.booking.dto.request.BookingRequest;
import com.spring.booking.dto.response.BookingResponse;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface BookingService {

    BookingResponse create(BookingRequest request);

    BookingResponse get(UUID id);

    BookingResponse update(UUID id, BookingRequest request);

    Page<BookingResponse> getAll(PageRequestDto pageRequestDtoParams);

    BookingResponse updateStatus(UUID id, BookingStatus newStatus);

    void updateStatusAndPaymentId(UUID id, BookingStatus status, String paymentId);

    void delete(UUID id);

    Page<BookingResponse> getByUser(Long userId, PageRequestDto pageRequestDto);

    Page<BookingResponse> getByHotel(Long hotelId, PageRequestDto pageRequestDto);

    Page<BookingResponse> getByStatus(BookingStatus status, PageRequestDto pageRequestDto);


}
