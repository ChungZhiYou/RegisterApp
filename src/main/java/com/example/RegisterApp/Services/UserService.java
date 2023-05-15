package com.example.RegisterApp.Services;

import com.example.RegisterApp.Models.UserEntity;
import com.example.RegisterApp.dto.UserDto;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserDto> findAllUsers();

    void saveUser(UserDto userDto);

    UserDto findById(Long userId);

    void updateUser(UserDto userDto);

    void deleteUser(Long userId);
    List<UserDto> searchUsers(String query);

    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByUsername(String username);
}
