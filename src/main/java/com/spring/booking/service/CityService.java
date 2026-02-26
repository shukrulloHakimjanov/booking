package com.spring.booking.service;

import com.spring.booking.dto.pageRequest.PageRequestDto;
import com.spring.booking.dto.request.CityRequest;
import com.spring.booking.dto.response.CityRepsonse;
import org.springframework.data.domain.Page;

public interface CityService {
    CityRepsonse create(CityRequest request);

    CityRepsonse get(Long id);

    CityRepsonse update(Long id, CityRequest request);

    Page<CityRepsonse> getAll(PageRequestDto pageRequestDtoParams);

    void delete(Long id);
}
