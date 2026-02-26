package com.spring.booking.service;

import com.spring.booking.dto.pageRequest.PageRequestDto;
import com.spring.booking.dto.request.ReviewRequest;
import com.spring.booking.dto.response.ReviewResponse;
import org.springframework.data.domain.Page;

public interface ReviewService {

    ReviewResponse create(ReviewRequest request);

    ReviewResponse get(Long id);

    ReviewResponse update(Long id, ReviewRequest request);

    Page<ReviewResponse> getAll(PageRequestDto pageRequestDtoParams);

    void delete(Long id);

    Page<ReviewResponse> getByHotelId(Long hotelId, PageRequestDto pageRequestDtoParams);

    Page<ReviewResponse> getByUserId(Long userId, PageRequestDto pageRequestDtoParams);

    Page<ReviewResponse> getByBookingId(Long bookingId, PageRequestDto pageRequestDtoParams);

    Page<ReviewResponse> getByMinRating(Integer minRating, PageRequestDto pageRequestDtoParams);

    Page<ReviewResponse> getByHotelIdAndMinRating(Long hotelId, Integer minRating, PageRequestDto pageRequestDtoParams);

}
