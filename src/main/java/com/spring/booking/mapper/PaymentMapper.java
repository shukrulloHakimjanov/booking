package com.spring.booking.mapper;


import com.spring.booking.dto.response.PaymentDTO;
import com.spring.booking.entity.Payment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    PaymentDTO toResponse(Payment roomTypes);

}
