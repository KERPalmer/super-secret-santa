package com.kenanpalmer.super_secret_santa.controllers;

import com.kenanpalmer.super_secret_santa.dto.CircleDTO;
import com.kenanpalmer.super_secret_santa.services.CircleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class HomeController {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final CircleService circleService;

    public HomeController(CircleService circleService) {
        this.circleService = circleService;
    }


    @GetMapping("/home")
    public String homePage(Model model, Principal principal) {

        if (principal == null){
            return "redirect/login";
        }

        String username = principal.getName();
        model.addAttribute("username", username);

        //fetch circles user is in
        List<CircleDTO> circles = circleService.getCirclesByUsername(username);
        model.addAttribute("circles", circles);
        return "home";
    }
}
