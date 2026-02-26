package com.spring.booking.mapper;


import com.spring.booking.dto.response.PaymentDTO;
import com.spring.booking.entity.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    @Mapping(target = "isActive",ignore = true)
    PaymentDTO toResponse(Payment roomTypes);

}
