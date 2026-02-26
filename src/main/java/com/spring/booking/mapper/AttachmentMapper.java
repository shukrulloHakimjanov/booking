package com.spring.booking.mapper;

import com.spring.booking.dto.response.AttachmentDto;
import com.spring.booking.entity.Attachments;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AttachmentMapper {

    AttachmentDto toDto(Attachments entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    Attachments toEntity(AttachmentDto dto);
}
