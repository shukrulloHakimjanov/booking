package com.spring.booking.dto.pageRequest;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class HotelFilterDto extends PageRequestDto {
    private Long cityId;
    private Long ownerId;
    private BigDecimal minRating;
    private Long amenityId;
    private Boolean isActive = true;
}
