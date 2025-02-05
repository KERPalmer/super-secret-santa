package com.kenanpalmer.super_secret_santa.controllers;

import com.kenanpalmer.super_secret_santa.Models.Circle;
import com.kenanpalmer.super_secret_santa.services.CircleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/circles")
public class CircleController {

    private final CircleService circleService;


    public CircleController(CircleService circleService) {
        this.circleService = circleService;
    }

    @GetMapping()
    public List<Circle> getAllCircles(){
        return circleService.getAllCircles();
    }

    @GetMapping("/{circleName}")
    public Circle getCircleByName(@PathVariable String circleName){
        return circleService.getCircleByName(circleName);
    }

    @PostMapping()
    public Circle createCircle(@RequestBody Circle circle){
        return circleService.createCircle(circle);
    }
}
