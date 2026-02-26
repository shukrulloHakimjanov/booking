package com.spring.booking.mapper;

import com.spring.booking.dto.request.BookingRequest;
import com.spring.booking.dto.response.BookingResponse;
import com.spring.booking.entity.Bookings;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BookingMapper {


    BookingResponse toResponse(Bookings entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Bookings toEntity(BookingRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void update(@MappingTarget Bookings entity, BookingRequest request);
}
