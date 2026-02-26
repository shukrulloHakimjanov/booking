package com.spring.booking.service;

import com.spring.booking.dto.pageRequest.PageRequestDto;
import com.spring.booking.dto.request.RoomTypeRequest;
import com.spring.booking.dto.response.RoomTypeResponse;
import org.springframework.data.domain.Page;

public interface RoomTypeService {
    RoomTypeResponse create(RoomTypeRequest request);

    RoomTypeResponse get(Long id);

    RoomTypeResponse update(Long id, RoomTypeRequest request);

    Page<RoomTypeResponse> getAll(PageRequestDto pageRequestDtoParams);

    void delete(Long id);

    Page<RoomTypeResponse> getByHotel(Long hotelId, PageRequestDto pageRequestDtoParams);

    Page<RoomTypeResponse> getByMinGuests(Integer minGuests, PageRequestDto pageRequestDtoParams);

    Page<RoomTypeResponse> getByHotelAndMinGuests(Long hotelId, Integer minGuests, PageRequestDto pageRequestDtoParams);

}
