package com.example.RegisterApp.Controllers;


import com.example.RegisterApp.Models.UserEntity;
import com.example.RegisterApp.Services.UserService;
import com.example.RegisterApp.dto.UserDto;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/users")
    public String listUsers(Model model){
        List<UserDto> userDtos = userService.findAllUsers();
        model.addAttribute("userEntities", userDtos); // inject users to webpage, present it to the frontend
        return "users-list";
    }

    @GetMapping("/register")
    public String createUserForm(Model model){
        UserDto userDto = new UserDto();
        model.addAttribute("userEntity", userDto);
        return "register";
    }

    @PostMapping("/register/save")
    public String saveUser(@Valid @ModelAttribute("userEntity") UserDto userDto,
                           BindingResult bindingResult,
                           Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("userEntity", userDto);
            return "redirect:/register?failRegister";
        }

        Optional<UserEntity> existingUserEmail = userService.findByEmail(userDto.getEmail());
        Optional<UserEntity> existingUsername = userService.findByUsername(userDto.getUsername());

        if(existingUsername.isEmpty() && existingUserEmail.isEmpty()){
            userService.saveUser(userDto);
            return "redirect:/login?successRegister";
        }
        return "redirect:/register?failRegister";
    }

    @GetMapping("/users/{userId}/edit")
    public String editUser(@PathVariable("userId") Long userId, Model model){
        UserDto userDto = userService.findById(userId);
        model.addAttribute("userEntity", userDto);
        return "users-edit";
    }

    @PostMapping("/users/{userId}/edit")
    public String updateUser(@PathVariable("userId") Long userId,
                             @Valid @ModelAttribute("userEntity") UserDto userDto,
                             BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "users-edit";
        }
        Optional<UserEntity> existingUserEmail = userService.findByEmail(userDto.getEmail());
        Optional<UserEntity> existingUsername = userService.findByUsername(userDto.getUsername());
        UserDto currentUser = userService.findById(userId);
        if(existingUsername.isEmpty() || currentUser.getUsername().equals(userDto.getUsername())){
            if (existingUserEmail.isEmpty() || currentUser.getEmail().equals(userDto.getEmail())) {
                userDto.setId(userId);
                userService.updateUser(userDto);
                return "redirect:/users?successEdit";
            }
        }
        return "redirect:/users/{userId}/edit?failEdit";
    }

    @GetMapping("/users/{userId}/delete")
    public String deleteUser(@PathVariable("userId") Long userId){
        userService.deleteUser(userId);
        return "redirect:/users";
    }

    @GetMapping("/users/search")
    public String searchUser(@RequestParam(value = "query") String query, Model model){
        List<UserDto> userDtos = userService.searchUsers(query);
        model.addAttribute("userEntities", userDtos);
        return "users-list";
    }

    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }
}
