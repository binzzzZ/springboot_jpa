package com.example.springboot_jpa.controller;

import com.example.springboot_jpa.dto.PageResponseDto;
import com.example.springboot_jpa.dto.UserRequestDto;
import com.example.springboot_jpa.dto.UserResponseDto;
import com.example.springboot_jpa.exception.ResourceNotFoundException;
import com.example.springboot_jpa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        List<UserResponseDto> users = userService.findAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/page")
    public ResponseEntity<PageResponseDto<UserResponseDto>> getUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "username,asc") String[] sort) {

        Pageable pageable = PageRequest.of(page, size, getSort(sort));
        PageResponseDto<UserResponseDto> users = userService.findAll(pageable);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable String id) {
        UserResponseDto user = userService.findById(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody UserRequestDto userRequestDto) {
        UserResponseDto createdUser = userService.create(userRequestDto);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(
            @PathVariable String id,
            @Valid @RequestBody UserRequestDto userRequestDto) {
        UserResponseDto updatedUser = userService.update(id, userRequestDto);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        if (!userService.existsById(id)) {
            throw new ResourceNotFoundException("User", id);
        }
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    private Sort getSort(String[] sort) {
        String property = sort[0];
        Sort.Direction direction = sort.length > 1 && sort[1].equalsIgnoreCase("desc") ?
                Sort.Direction.DESC : Sort.Direction.ASC;
        return Sort.by(direction, property);
    }
}
