package com.spring.booking.service;

import com.spring.booking.constant.enums.Role;
import com.spring.booking.dto.auth.UserAuth;
import com.spring.booking.dto.pageRequest.PageRequestDto;
import com.spring.booking.dto.request.UserRequest;
import com.spring.booking.dto.response.UserResponse;
import org.springframework.data.domain.Page;

import java.sql.SQLDataException;

public interface UserService {

    UserResponse create(UserRequest request);

    UserResponse get(Long id);

    UserResponse update(Long id, UserRequest request) throws SQLDataException;

    Page<UserResponse> getAll(PageRequestDto pageRequestDtoParams);

    void delete(Long id);

    Page<UserResponse> getByRole(Role role, PageRequestDto pageRequestDtoParams);

    UserAuth getUserAuthByEmail(String email);
}
