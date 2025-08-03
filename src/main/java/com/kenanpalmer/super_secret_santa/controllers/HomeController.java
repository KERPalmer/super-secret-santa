package com.kenanpalmer.super_secret_santa.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class HomeController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @GetMapping("/home")
    public String homePage(Model model, Principal principal) {
        if (principal != null) {
            model.addAttribute("username", principal.getName());
        } else {
            return null;
        }
        return "home";
    }
}
