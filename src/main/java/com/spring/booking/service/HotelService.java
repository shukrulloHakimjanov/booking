package com.spring.booking.service;

import com.spring.booking.dto.pageRequest.HotelFilterDto;
import com.spring.booking.dto.request.HotelRequest;
import com.spring.booking.dto.response.HotelResponse;
import org.springframework.data.domain.Page;

public interface HotelService {

    HotelResponse create(HotelRequest request);

    HotelResponse get(Long id);

    HotelResponse update(Long id, HotelRequest request);

    Page<HotelResponse> getAll(HotelFilterDto filterParams);

    void delete(Long id);
}
