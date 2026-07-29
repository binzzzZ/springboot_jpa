package com.example.springboot_jpa.service;

import com.example.springboot_jpa.dto.UserRequestDto;
import com.example.springboot_jpa.dto.UserResponseDto;
import com.example.springboot_jpa.dto.PageResponseDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {

    List<UserResponseDto> findAll();

    PageResponseDto<UserResponseDto> findAll(Pageable pageable);

    UserResponseDto findById(String id);

    UserResponseDto create(UserRequestDto userRequestDto);

    UserResponseDto update(String id, UserRequestDto userRequestDto);

    void delete(String id);

    boolean existsById(String id);
}
