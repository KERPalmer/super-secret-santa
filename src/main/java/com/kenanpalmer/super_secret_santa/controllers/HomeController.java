package com.kenanpalmer.super_secret_santa.controllers;

import com.kenanpalmer.super_secret_santa.dto.CircleResponseDTO;
import com.kenanpalmer.super_secret_santa.services.CircleService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class HomeController {

    private final CircleService circleService;

    public HomeController(CircleService circleService) {
        this.circleService = circleService;
    }


    @GetMapping("/home")
    public String homePage(Model model, Principal principal) {

        if (principal == null){
            return "redirect/login";
        }

        String principalName = principal.getName();
        model.addAttribute("username", principalName);

        //fetch circles user is in
        List<CircleResponseDTO> circles = circleService.getCirclesByUsername(principalName);
        model.addAttribute("circles", circles);
        return "home";
    }
}
