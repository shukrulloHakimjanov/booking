package com.spring.booking.mapper;

import com.spring.booking.dto.request.AmenityRequest;
import com.spring.booking.dto.response.AmenityResponse;
import com.spring.booking.entity.Amenities;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AmenityMapper {
    AmenityResponse toDto(Amenities entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "isActive",ignore = true)
    Amenities fromDto(AmenityRequest dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active",ignore = true)
    void update(@MappingTarget Amenities ent, AmenityRequest request);
}
