package com.example.springboot_jpa.service.impl;

import com.example.springboot_jpa.dao.UserDao;
import com.example.springboot_jpa.dto.UserRequestDto;
import com.example.springboot_jpa.dto.UserResponseDto;
import com.example.springboot_jpa.dto.PageResponseDto;
import com.example.springboot_jpa.entity.User;
import com.example.springboot_jpa.exception.BadRequestException;
import com.example.springboot_jpa.exception.ResourceNotFoundException;
import com.example.springboot_jpa.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public List<UserResponseDto> findAll() {
        return userDao.findAll().stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public PageResponseDto<UserResponseDto> findAll(Pageable pageable) {
        Page<User> userPage = userDao.findAll(pageable);

        List<UserResponseDto> content = userPage.getContent().stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());

        return new PageResponseDto<>(
                content,
                userPage.getNumber(),
                userPage.getSize(),
                userPage.getTotalElements(),
                userPage.getTotalPages(),
                userPage.isFirst(),
                userPage.isLast()
        );
    }

    @Override
    public UserResponseDto findById(String id) {
        User user = userDao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", id));
        return convertToResponseDto(user);
    }

    @Override
    public UserResponseDto create(UserRequestDto userRequestDto) {
        validateUserRequest(userRequestDto);

        // Check if username already exists
        Optional<User> existingUser = userDao.findByUsername(userRequestDto.getUsername());
        if (existingUser.isPresent()) {
            throw new BadRequestException("Username already exists: " + userRequestDto.getUsername());
        }

        User user = convertToEntity(userRequestDto);
        user.setUserId(java.util.UUID.randomUUID().toString());

        LocalDateTime now = LocalDateTime.now();
        user.setCreatedAt(now);
        user.setUpdatedAt(now);

        User savedUser = userDao.save(user);
        return convertToResponseDto(savedUser);
    }

    @Override
    public UserResponseDto update(String id, UserRequestDto userRequestDto) {
        validateUserRequest(userRequestDto);

        User existingUser = userDao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", id));

        // Check if username is already taken by another user
        if (!existingUser.getUsername().equals(userRequestDto.getUsername())) {
            Optional<User> userWithSameUsername = userDao.findByUsername(userRequestDto.getUsername());
            if (userWithSameUsername.isPresent()) {
                throw new BadRequestException("Username already exists: " + userRequestDto.getUsername());
            }
        }

        BeanUtils.copyProperties(userRequestDto, existingUser);
        existingUser.setUpdatedAt(LocalDateTime.now());

        User updatedUser = userDao.save(existingUser);
        return convertToResponseDto(updatedUser);
    }

    @Override
    public void delete(String id) {
        User user = userDao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", id));
        userDao.delete(user);
    }

    @Override
    public boolean existsById(String id) {
        return userDao.existsById(id);
    }

    private void validateUserRequest(UserRequestDto userRequestDto) {
        if (userRequestDto.getUsername() == null || userRequestDto.getUsername().trim().isEmpty()) {
            throw new BadRequestException("Username cannot be empty");
        }
        if (userRequestDto.getPassword() == null || userRequestDto.getPassword().trim().isEmpty()) {
            throw new BadRequestException("Password cannot be empty");
        }
    }

    private UserResponseDto convertToResponseDto(User user) {
        UserResponseDto responseDto = new UserResponseDto();
        BeanUtils.copyProperties(user, responseDto);
        responseDto.setCreatedAt(user.getCreatedAt());
        responseDto.setUpdatedAt(user.getUpdatedAt());
        return responseDto;
    }

    private User convertToEntity(UserRequestDto userRequestDto) {
        User user = new User();
        BeanUtils.copyProperties(userRequestDto, user);
        return user;
    }
}