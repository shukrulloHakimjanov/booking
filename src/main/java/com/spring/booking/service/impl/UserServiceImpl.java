package com.spring.booking.service.impl;

import com.spring.booking.constant.enums.Role;
import com.spring.booking.constant.enums.UserStatus;
import com.spring.booking.dto.auth.UserAuth;
import com.spring.booking.dto.pageRequest.PageRequestDto;
import com.spring.booking.dto.request.UserRequest;
import com.spring.booking.dto.response.UserResponse;
import com.spring.booking.entity.User;
import com.spring.booking.exception.exception409.UserEmailAlreadyExistsException;
import com.spring.booking.exception.exceptions404.UserNotFoundException;
import com.spring.booking.mapper.UserMapper;
import com.spring.booking.repository.UserRepository;
import com.spring.booking.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {

    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;

    @Override
    public UserResponse create(UserRequest request) {
        User user = userMapper.fromDto(request);
        validateEmailUnique(user.getEmail());
        user.setPasswordHash(passwordEncoder.encode(request.password()));
        user.setStatus(UserStatus.ACTIVE);
        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public UserResponse get(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));

        return userMapper.toDto(user);
    }

    @Override
    public UserResponse update(Long id, UserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));

        userMapper.update(user, request);

        if (request.password() != null && !request.password().isBlank()) {
            user.setPasswordHash(passwordEncoder.encode(request.password()));
        }

        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }

    @Override
    public Page<UserResponse> getAll(PageRequestDto pageRequestDtoParams) {
        Page<User> all = userRepository.findAll(pageRequestDtoParams.getPageable());
        return all.map(userMapper::toDto);
    }

    @Override
    public Page<UserResponse> getByRole(Role role, PageRequestDto pageRequestDtoParams) {
        Page<User> users = userRepository.findByRole(role, pageRequestDtoParams.getPageable());
        return users.map(userMapper::toDto);
    }

    private void validateEmailUnique(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new UserEmailAlreadyExistsException("Email already exists: " + email);
        }
    }

    public UserAuth getUserAuthByEmail(String email) {
        return userRepository.findByEmail((email))
                .map(userMapper::toUserAuth)
                .orElseThrow();
    }
}
