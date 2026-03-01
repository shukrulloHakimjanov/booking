package com.spring.booking.dto.projections;

public record IdKeyDto (
        Long id,
        String key,
        String originalName
){
}
