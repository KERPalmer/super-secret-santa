package com.kenanpalmer.super_secret_santa.controllers;

import com.kenanpalmer.super_secret_santa.converter.UserRegisterDTOToUserConverter;
import com.kenanpalmer.super_secret_santa.dto.user.UserRegisterDTO;
import com.kenanpalmer.super_secret_santa.dto.user.UserSummaryDTO;
import com.kenanpalmer.super_secret_santa.services.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Objects;
import java.util.Optional;

@Controller
public class LoginController {

    private static final String ERROR = "error";
    Logger logger = LoggerFactory.getLogger(getClass());

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
    public String showRegistrationForm(Model model) {
        model.addAttribute("userRegisterDTO", new UserRegisterDTO("", "", ""));
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute @Valid @NotNull UserRegisterDTO user,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes){

        if (bindingResult.hasErrors()){
            logger.error("binding has errors");
            redirectAttributes.addAttribute(ERROR, "invalid entries");
            return "register";
        }

        logger.info("creating user with username {}, password: {}, repeatedPassword: {}",
                user.getUsername(), user.getPassword(), user.getRepeatedPassword());

        if(!user.getPassword().equals(user.getRepeatedPassword())){
            logger.error("unequal passwords");
            redirectAttributes.addAttribute(ERROR, "passwords do not match");
            return "redirect:/register";
        }

        Optional<UserSummaryDTO> userSummary = userService.registerUser(
                Objects.requireNonNull(userRegisterDTOToUserConverter.convert(user))
        );

        if(userSummary.isEmpty()){
            redirectAttributes.addAttribute(ERROR, "error registering user");
            return "redirect:/register";
        }

        return "redirect:/login";
    }
}
