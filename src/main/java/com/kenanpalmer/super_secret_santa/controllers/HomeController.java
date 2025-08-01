package com.kenanpalmer.super_secret_santa.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class HomeController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @GetMapping("/home")
    public String homePage(Principal principal) {
        logger.info("logged in user is: {}", principal.getName());
        return "home";
    }
}
