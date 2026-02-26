package com.spring.booking.mapper;

import com.spring.booking.dto.request.ReviewRequest;
import com.spring.booking.dto.response.ReviewResponse;
import com.spring.booking.entity.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    ReviewResponse toResponse(Review entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    @Mapping(target = "booking", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "hotel", ignore = true)
    Review toEntity(ReviewRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "booking", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "hotel", ignore = true)
    void update(@MappingTarget Review entity, ReviewRequest request);
}
