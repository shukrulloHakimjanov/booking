package com.spring.booking.service;

import com.spring.booking.dto.pageRequest.PageRequestDto;
import com.spring.booking.dto.request.AmenityRequest;
import com.spring.booking.dto.response.AmenityResponse;
import org.springframework.data.domain.Page;

public interface AmenityService {
    AmenityResponse create(AmenityRequest request);

    AmenityResponse get(Long id);

    AmenityResponse update(Long id, AmenityRequest request);

    Page<AmenityResponse> getAll(PageRequestDto pageRequestDtoParams);

    void delete(Long id);
}
