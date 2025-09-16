package com.kenanpalmer.super_secret_santa.controllers.advisors;


import com.kenanpalmer.super_secret_santa.exception.UsernameAlreadyRegisteredException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UsernameAlreadyRegisteredException.class)
    public String handleUsernameAlreadyRegistered(UsernameAlreadyRegisteredException ex, RedirectAttributes redirectAttributes){
        redirectAttributes.addAttribute("error", "username already registered, use a different one");
        redirectAttributes.addFlashAttribute("errorMessage",
                "Username already registered, use a different one");
        return "redirect:/register";
    }


    //generic one
    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        model.addAttribute("errorClass", ex.getClass().getSimpleName());
        return "error";
    }
}
