package com.spring.booking.service;

import com.spring.booking.constant.enums.Status;
import com.spring.booking.dto.pageRequest.PageRequestDto;
import com.spring.booking.dto.request.RoomRequest;
import com.spring.booking.dto.response.RoomResponse;
import org.springframework.data.domain.Page;

public interface RoomService {
    RoomResponse create(RoomRequest request);

    RoomResponse update(Long id, RoomRequest request);

    RoomResponse get(Long id);

    RoomResponse updateStatus(Long id, Status status);

    Page<RoomResponse> getAllWithSearch(PageRequestDto pageRequestDtoParams);

    Page<RoomResponse> getByRoomTypeId(Long roomTypeId, PageRequestDto pageRequestDtoParams);

    Page<RoomResponse> getByHotelId(Long hotelId, PageRequestDto pageRequestDtoParams);

    Page<RoomResponse> getByStatus(Status status, PageRequestDto pageRequestDtoParams);

    Page<RoomResponse> getByFloor(Integer floor, PageRequestDto pageRequestDtoParams);

    Page<RoomResponse> getByHotelIdAndStatus(Long hotelId, Status status, PageRequestDto pageRequestDtoParams);

    void deleteById(Long id);
}