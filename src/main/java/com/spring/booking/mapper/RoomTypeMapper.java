package com.spring.booking.mapper;

import com.spring.booking.dto.request.RoomTypeRequest;
import com.spring.booking.dto.response.RoomTypeResponse;
import com.spring.booking.entity.RoomTypes;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface RoomTypeMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    @Mapping(target = "hotel", ignore = true)
    @Mapping(target = "amenities", ignore = true)
    RoomTypes toEntity(RoomTypeRequest dto);

    RoomTypeResponse toResponse(RoomTypes roomTypes);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "hotel", ignore = true)
    @Mapping(target = "amenities", ignore = true)
    void update(@MappingTarget RoomTypes ent, RoomTypeRequest request);

}
