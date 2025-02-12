package com.kenanpalmer.super_secret_santa.controllers;

import com.kenanpalmer.super_secret_santa.Models.Circle;
import com.kenanpalmer.super_secret_santa.dto.CircleDTO;
import com.kenanpalmer.super_secret_santa.dto.UsernameDTO;
import com.kenanpalmer.super_secret_santa.services.CircleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/circles")
public class CircleController {

    private final Logger LOGGER = LoggerFactory.getLogger(CircleController.class);
    private final CircleService circleService;


    public CircleController(CircleService circleService) {
        this.circleService = circleService;
    }

    @GetMapping()
    public ResponseEntity<List<CircleDTO>> getAllCircles(){
        return ResponseEntity.ok()
                .body(circleService.getAllCircles());
    }

    @GetMapping("/{circleName}")
    public ResponseEntity<CircleDTO> getCircleByName(@PathVariable String circleName){
        return ResponseEntity.ok()
                .body(circleService.getCircleByName(circleName));
    }

    @PostMapping()
    public ResponseEntity<Circle> createCircle(@RequestBody Circle circle){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(circleService.createCircle(circle));
    }

    @PostMapping("/{circleName}")
    public ResponseEntity<CircleDTO> addUserToCircle(@PathVariable String circleName, @RequestBody UsernameDTO usernameDTO){
        return  ResponseEntity.status(HttpStatus.CREATED)
                .body(circleService.addUserToCircle(circleName, usernameDTO));
    }
}
