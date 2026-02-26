package com.spring.booking.mapper;

import com.spring.booking.dto.request.CityRequest;
import com.spring.booking.dto.response.CityRepsonse;
import com.spring.booking.entity.City;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CitiesMapper {
    CityRepsonse toDto(City entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "isActive",ignore = true)
    City fromDto(CityRequest dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    void update(@MappingTarget City ent, CityRequest request);
}
