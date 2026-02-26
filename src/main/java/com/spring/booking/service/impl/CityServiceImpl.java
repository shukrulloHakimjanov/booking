package com.spring.booking.service.impl;

import com.spring.booking.dto.pageRequest.PageRequestDto;
import com.spring.booking.dto.request.CityRequest;
import com.spring.booking.dto.response.CityRepsonse;
import com.spring.booking.entity.City;
import com.spring.booking.exception.exceptions404.CitiesNotFoundException;
import com.spring.booking.mapper.CitiesMapper;
import com.spring.booking.repository.CitiesRepository;
import com.spring.booking.service.CityService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional(readOnly = true)
public class CityServiceImpl implements CityService {
    CitiesRepository citiesRepository;
    CitiesMapper citiesMapper;

    @Override
    @Transactional
    public CityRepsonse create(CityRequest request) {
        City city = citiesMapper.fromDto(request);
        city.setActive(true);
        return citiesMapper.toDto(citiesRepository.save(city));

    }

    @Override
    public CityRepsonse get(Long id) {
        City city = citiesRepository.findById(id)
                .orElseThrow(() -> new CitiesNotFoundException("City not found with id: "));
        return citiesMapper.toDto(city);
    }

    @Override
    @Transactional
    public CityRepsonse update(Long id, CityRequest request) {
        City city = citiesRepository.findById(id)
                .orElseThrow(() -> new CitiesNotFoundException("User not found with id: "));

        citiesMapper.update(city, request);

        return citiesMapper.toDto(citiesRepository.save(city));

    }

    @Override
    public Page<CityRepsonse> getAll(PageRequestDto pageRequestDtoParams) {
        Page<City> all = citiesRepository.findAllWithSearch(pageRequestDtoParams.getSearchText(), pageRequestDtoParams.getPageable());
        return all.map(citiesMapper::toDto);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!citiesRepository.existsById(id)) {
            throw new CitiesNotFoundException("City not found with id: " + id);
        }
        citiesRepository.deleteById(id);
    }
}
