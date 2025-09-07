package com.kenanpalmer.super_secret_santa.controllers;

import com.kenanpalmer.super_secret_santa.dto.CircleRequestDTO;
import com.kenanpalmer.super_secret_santa.dto.CircleResponseDTO;
import com.kenanpalmer.super_secret_santa.services.CircleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/circles")
public class CircleController {

    private final Logger LOGGER = LoggerFactory.getLogger(CircleController.class);
    private final CircleService circleService;


    public CircleController(CircleService circleService) {
        this.circleService = circleService;
    }

    @GetMapping()
    public ResponseEntity<List<CircleResponseDTO>> getAllCircles() {
        return ResponseEntity.ok()
                .body(circleService.getAllCircles());
    }

    @GetMapping("/create")
    public String showCreateCirclePage(Model model) {
        LOGGER.info("show be getting create circle page");
        model.addAttribute("circle", new CircleRequestDTO());
        return "create-circle";
    }

    @PostMapping()
    public ResponseEntity<CircleResponseDTO> createCircle(@ModelAttribute CircleRequestDTO circle) {
        LOGGER.info("creating circle with name:{}", circle.getName());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(circleService.createCircle(circle));
    }
}
