package com.spring.booking.service.impl;

import com.spring.booking.dto.pageRequest.HotelFilterDto;
import com.spring.booking.dto.request.HotelRequest;
import com.spring.booking.dto.response.HotelResponse;
import com.spring.booking.entity.City;
import com.spring.booking.entity.Hotels;
import com.spring.booking.exception.exceptions404.CitiesNotFoundException;
import com.spring.booking.exception.exceptions404.HotelNotFoundException;
import com.spring.booking.mapper.HotelMapper;
import com.spring.booking.repository.AmenityRepository;
import com.spring.booking.repository.AttachmentRepository;
import com.spring.booking.repository.CitiesRepository;
import com.spring.booking.repository.HotelRepository;
import com.spring.booking.service.HotelService;
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
public class HotelServiceImpl implements HotelService {

    HotelRepository hotelRepository;
    AttachmentRepository attachmentRepository;
    AmenityRepository amenityRepository;
    HotelMapper hotelMapper;
    CitiesRepository citiesRepository;

    @Override
    @Transactional
    public HotelResponse create(HotelRequest request) {
        Hotels hotel = hotelMapper.toEntity(request);
        City city = citiesRepository.findById(request.cityId()).orElseThrow(() -> new CitiesNotFoundException("City not found with id: " + request.cityId()));

        hotel.setCity(city);

        setAttachmentsAndAmenities(hotel, request);

        Hotels saved = hotelRepository.save(hotel);
        return hotelMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public HotelResponse update(Long id, HotelRequest request) {
        Hotels hotel = hotelRepository.findById(id).orElseThrow(() -> new HotelNotFoundException("Hotel not found with id: " + id));

        hotelMapper.update(hotel, request);

        setAttachmentsAndAmenities(hotel, request);

        Hotels updated = hotelRepository.save(hotel);
        return hotelMapper.toResponse(updated);
    }

    private void setAttachmentsAndAmenities(Hotels hotel, HotelRequest request) {

        if (request.attachmentIds() != null) {
            hotel.setAttachments(new HashSet<>(attachmentRepository.findAllById(request.attachmentIds())));
        }

        if (request.amenitiesIds() != null) {
            hotel.setAmenities(new HashSet<>(amenityRepository.findAllById(request.amenitiesIds())));
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!hotelRepository.existsById(id)) {
            throw new HotelNotFoundException("Hotel not found with id: " + id);
        }
        hotelRepository.deleteById(id);
    }

    @Override
    public Page<HotelResponse> getAll(HotelFilterDto filterParams) {
        Page<Hotels> page = hotelRepository.searchHotels(filterParams.getSearchText(), filterParams.getCityId(), filterParams.getOwnerId(), filterParams.getMinRating(), filterParams.getAmenityId(), filterParams.getIsActive(), filterParams.getPageable());
        return page.map(hotelMapper::toResponse);
    }


    @Override
    public HotelResponse get(Long id) {
        Hotels hotel = hotelRepository.findById(id).orElseThrow(() -> new HotelNotFoundException("Hotel not found with id: " + id));
        return hotelMapper.toResponse(hotel);
    }

}
