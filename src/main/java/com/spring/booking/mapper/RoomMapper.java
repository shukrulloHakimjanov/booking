package com.spring.booking.mapper;

import com.spring.booking.dto.request.RoomRequest;
import com.spring.booking.dto.response.RoomResponse;
import com.spring.booking.entity.Rooms;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface RoomMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    @Mapping(target = "roomType", ignore = true)
    Rooms toEntity(RoomRequest dto);

    RoomResponse toResponse(Rooms ent);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "roomType", ignore = true)
    void update(@MappingTarget Rooms ent, RoomRequest request);

}
