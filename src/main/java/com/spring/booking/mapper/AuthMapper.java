package com.spring.booking.mapper;

import com.spring.booking.dto.auth.SignupDTO;
import com.spring.booking.dto.auth.UserAuth;
import com.spring.booking.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AuthMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    User fromDto(SignupDTO dto);

    UserAuth toUserAuth(User entity);


}
