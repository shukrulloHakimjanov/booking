package com.spring.booking.dto.response;

public record AttachmentDto(
        Long id,
        String link,
        String key,
        String originalName,
        String contentType,
        String bucketName
) {

}