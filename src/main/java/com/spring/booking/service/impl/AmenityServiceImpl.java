package com.spring.booking.service.impl;

import com.spring.booking.dto.pageRequest.PageRequestDto;
import com.spring.booking.dto.request.AmenityRequest;
import com.spring.booking.dto.response.AmenityResponse;
import com.spring.booking.entity.Amenities;
import com.spring.booking.exception.AlreadyExistsException;
import com.spring.booking.exception.NotFoundException;
import com.spring.booking.mapper.AmenityMapper;
import com.spring.booking.repository.AmenityRepository;
import com.spring.booking.service.AmenityService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional(readOnly = true)
public class AmenityServiceImpl implements AmenityService {

    AmenityRepository amenityRepository;
    AmenityMapper amenityMapper;

    @Override
    @Transactional
    public AmenityResponse create(AmenityRequest request) {
        Amenities amenity = amenityMapper.fromDto(request);
        Amenities saved = amenityRepository.save(amenity);
        return amenityMapper.toDto(saved);
    }

    @Override
    public AmenityResponse get(Long id) {
        Amenities amenity = amenityRepository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException("Amenity not found with id: " + id)
                );
        return amenityMapper.toDto(amenity);
    }

    @Override
    @Transactional
    public AmenityResponse update(Long id, AmenityRequest request) {
        Amenities amenity = amenityRepository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException("Amenity not found with id: " + id)
                );

        if (Objects.equals(request.name(), amenity.getName())) {
            throw new AlreadyExistsException("Amenity already exists!");
        }

        amenityMapper.update(amenity, request);
        Amenities updated = amenityRepository.save(amenity);

        return amenityMapper.toDto(updated);
    }

    @Override
    public Page<AmenityResponse> getAll(PageRequestDto pageRequestDtoParams) {
        Page<Amenities> page =
                amenityRepository.findAllWithSearch(
                        pageRequestDtoParams.getSearchText(),
                        pageRequestDtoParams.getPageable()
                );

        return page.map(amenityMapper::toDto);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!amenityRepository.existsById(id)) {
            throw new NotFoundException("Amenity not found with id: " + id);
        }
        amenityRepository.deleteById(id);
    }

}
