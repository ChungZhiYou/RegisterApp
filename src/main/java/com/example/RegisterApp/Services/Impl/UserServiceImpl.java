package com.example.RegisterApp.Services.Impl;

import com.example.RegisterApp.Models.UserEntity;
import com.example.RegisterApp.Repository.RoleRepository;
import com.example.RegisterApp.Repository.UserRepository;
import com.example.RegisterApp.Services.UserService;
import com.example.RegisterApp.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository,PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public List<UserDto> findAllUsers(){
        List<UserEntity> userEntities = userRepository.findAll();
        return userEntities.stream().map((user) -> mapToUserDto(user)).collect(Collectors.toList());
    }

    @Override
    public void saveUser(UserDto userDto) {
        userRepository.save(mapToUser(userDto));
    }

    @Override
    public UserDto findById(Long userId) {
        UserEntity userEntity = userRepository.findById(userId).get();
        UserDto userDto = mapToUserDto(userEntity);
        return userDto;
    }

    @Override
    public void updateUser(UserDto userDto) {
        UserEntity userEntity = mapToUser(userDto);
        userRepository.save(userEntity);
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public List<UserDto> searchUsers(String query) {
        List<UserEntity> userEntities = userRepository.searchUsers(query);
        return userEntities.stream().map((user) -> mapToUserDto(user)).collect(Collectors.toList());
    }

    @Override
    public Optional<UserEntity> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<UserEntity> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    private UserEntity mapToUser(UserDto userDto) {
        return UserEntity.builder()
                .id(userDto.getId())
                .email(userDto.getEmail())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .username(userDto.getUsername())
                .roles(Arrays.asList(roleRepository.findByName("ROLE_USER")))
                .build();
    }

    private UserDto mapToUserDto(UserEntity userEntity){
        return UserDto.builder()
                .id(userEntity.getId())
                .email(userEntity.getEmail())
                .password(userEntity.getPassword())
                .username(userEntity.getUsername())
                .build();
    }
}
