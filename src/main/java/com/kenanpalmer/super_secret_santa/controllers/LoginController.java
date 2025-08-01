package com.kenanpalmer.super_secret_santa.controllers;

import com.kenanpalmer.super_secret_santa.converter.UserRegisterDTOToUserConverter;
import com.kenanpalmer.super_secret_santa.dto.user.UserRegisterDTO;
import com.kenanpalmer.super_secret_santa.dto.user.UserSummaryDTO;
import com.kenanpalmer.super_secret_santa.services.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class LoginController {

    UserService userService;
    UserRegisterDTOToUserConverter userRegisterDTOToUserConverter;

    public LoginController(UserService userService, UserRegisterDTOToUserConverter userRegisterDTOToUserConverter) {
        this.userService = userService;
        this.userRegisterDTOToUserConverter = userRegisterDTOToUserConverter;
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(){
        return "register";
    }

    @PostMapping("Register")
    public String registerUser(@ModelAttribute @Valid @NotNull UserRegisterDTO user, RedirectAttributes redirectAttributes){

        if(user.getPassword().equals(user.getRepeatedPassword())){
            redirectAttributes.addAttribute("error", "passwords do not match");
            return "redirect:/register";
        }

        Optional<UserSummaryDTO> userSummary = userService.registerUser(userRegisterDTOToUserConverter.convert(user));

        if(userSummary.isEmpty()){
            redirectAttributes.addAttribute("error", "error registering user");
            return "redirect:/register";
        }

        return "redirect:/login";
    }
}
