package com.example.RegisterApp.Config;

import com.example.RegisterApp.Models.Role;
import com.example.RegisterApp.Models.UserEntity;
import com.example.RegisterApp.Repository.RoleRepository;
import com.example.RegisterApp.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;

@Configuration
public class UserConfig {
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    CommandLineRunner userCommandLineRunner(UserRepository userRepository, RoleRepository roleRepository){
        return args -> {
            Role admin = new Role(1L, "ROLE_ADMIN", Arrays.asList());
            Role user = new Role(2L, "ROLE_USER", Arrays.asList());
//            UserEntity john = new UserEntity(1L, "JohnDoe", "JohnDoe@gmail.com", passwordEncoder.encode("John123"), Arrays.asList(user));
//            UserEntity alex = new UserEntity(2L, "Alex", "Alex@gmail.com", passwordEncoder.encode("Alex123"), Arrays.asList(user));
//            UserEntity jeniffer = new UserEntity(3L, "Jeniffer", "Jeniffer@gmail.com", passwordEncoder.encode("Jeniffer123"), Arrays.asList(admin));
            roleRepository.saveAll(List.of(admin,user));
//            userRepository.saveAll(List.of(john,alex,jeniffer));
        };
    }
}
