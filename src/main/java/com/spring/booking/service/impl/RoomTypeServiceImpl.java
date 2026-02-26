package com.spring.booking.service.impl;

import com.spring.booking.dto.pageRequest.PageRequestDto;
import com.spring.booking.dto.request.RoomTypeRequest;
import com.spring.booking.dto.response.RoomTypeResponse;
import com.spring.booking.entity.Hotels;
import com.spring.booking.entity.RoomTypes;
import com.spring.booking.exception.BadRequestException;
import com.spring.booking.exception.exceptions404.HotelNotFoundException;
import com.spring.booking.exception.exceptions404.RoomTypeNotFoundException;
import com.spring.booking.mapper.RoomTypeMapper;
import com.spring.booking.repository.AmenityRepository;
import com.spring.booking.repository.HotelRepository;
import com.spring.booking.repository.RoomTypeRepository;
import com.spring.booking.service.RoomTypeService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional(readOnly = true)
public class RoomTypeServiceImpl implements RoomTypeService {

    RoomTypeRepository roomTypeRepository;
    HotelRepository hotelRepository;
    AmenityRepository amenityRepository;
    RoomTypeMapper roomTypeMapper;

    @Override
    @Transactional
    public RoomTypeResponse create(RoomTypeRequest request) {

        Hotels hotel = hotelRepository.findById(request.hotelId())
                .orElseThrow(() -> new HotelNotFoundException("Hotel not found with id: " + request.hotelId()));

        RoomTypes roomType = roomTypeMapper.toEntity(request);
        roomType.setHotel(hotel);

        setAmenities(roomType, request);

        RoomTypes saved = roomTypeRepository.save(roomType);
        return roomTypeMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public RoomTypeResponse update(Long id, RoomTypeRequest request) {

        RoomTypes roomType = roomTypeRepository.findById(id).orElseThrow(() -> new RoomTypeNotFoundException("Room type not found with id: " + id));

        roomTypeMapper.update(roomType, request);

        if (request.hotelId() != null) {
            Hotels hotel = hotelRepository.findById(request.hotelId()).orElseThrow(() -> new HotelNotFoundException("Hotel not found with id: " + request.hotelId()));
            roomType.setHotel(hotel);
        }

        setAmenities(roomType, request);

        RoomTypes updated = roomTypeRepository.save(roomType);
        return roomTypeMapper.toResponse(updated);
    }

    @Override
    public RoomTypeResponse get(Long id) {
        RoomTypes roomType = roomTypeRepository.findById(id).orElseThrow(() -> new RoomTypeNotFoundException("Room type not found with id: " + id));

        return roomTypeMapper.toResponse(roomType);
    }

    @Override
    public Page<RoomTypeResponse> getAll(PageRequestDto pageRequestDtoParams) {
        Page<RoomTypes> page = roomTypeRepository.findAllWithSearch(pageRequestDtoParams.getSearchText(), pageRequestDtoParams.getPageable());
        return page.map(roomTypeMapper::toResponse);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!roomTypeRepository.existsById(id)) {
            throw new RoomTypeNotFoundException("Room type not found with id: " + id);
        }
        roomTypeRepository.deleteById(id);
    }

    private void setAmenities(RoomTypes roomType, RoomTypeRequest request) {
        if (request.amenitiesIds() != null) {
            roomType.setAmenities(new HashSet<>(amenityRepository.findAllById(request.amenitiesIds())));
        }
    }

    @Override
    public Page<RoomTypeResponse> getByHotel(Long hotelId, PageRequestDto pageRequestDtoParams) {
        validateHotelExists(hotelId);
        Page<RoomTypes> page = roomTypeRepository.findByHotelId(hotelId, pageRequestDtoParams.getPageable());
        return page.map(roomTypeMapper::toResponse);
    }

    @Override
    public Page<RoomTypeResponse> getByMinGuests(Integer minGuests, PageRequestDto pageRequestDtoParams) {
        if (minGuests == null || minGuests < 1) {
            throw new BadRequestException("minGuests must be greater than 0");
        }
        Page<RoomTypes> page = roomTypeRepository.findByMinGuests(minGuests, pageRequestDtoParams.getPageable());
        return page.map(roomTypeMapper::toResponse);
    }

    @Override
    public Page<RoomTypeResponse> getByHotelAndMinGuests(Long hotelId, Integer minGuests, PageRequestDto pageRequestDtoParams) {
        validateHotelExists(hotelId);
        if (minGuests == null || minGuests < 1) {
            throw new BadRequestException("minGuests must be greater than 0");
        }
        Page<RoomTypes> page = roomTypeRepository.findByHotelIdAndMinGuests(hotelId, minGuests, pageRequestDtoParams.getPageable());
        return page.map(roomTypeMapper::toResponse);
    }

    private void validateHotelExists(Long hotelId) {
        if (!hotelRepository.existsById(hotelId)) {
            throw new HotelNotFoundException("Hotel not found with id: " + hotelId);
        }
    }


}
