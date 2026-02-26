package com.spring.booking.mapper;

import com.spring.booking.dto.request.HotelRequest;
import com.spring.booking.dto.response.HotelResponse;
import com.spring.booking.entity.Hotels;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface HotelMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    @Mapping(target = "owner", ignore = true)
    @Mapping(target = "city", ignore = true)
    @Mapping(target = "attachments", ignore = true)
    @Mapping(target = "amenities", ignore = true)
    Hotels toEntity(HotelRequest dto);

    HotelResponse toResponse(Hotels hotel);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "owner", ignore = true)
    @Mapping(target = "city", ignore = true)
    @Mapping(target = "attachments", ignore = true)
    @Mapping(target = "amenities", ignore = true)
    void update(@MappingTarget Hotels ent, HotelRequest request);

}
