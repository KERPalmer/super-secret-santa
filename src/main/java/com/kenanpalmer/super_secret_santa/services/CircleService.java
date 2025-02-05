package com.kenanpalmer.super_secret_santa.services;

import com.kenanpalmer.super_secret_santa.Models.Circle;
import com.kenanpalmer.super_secret_santa.Repositories.CircleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CircleService {
    private final CircleRepository circleRepository;

    public CircleService(CircleRepository circleRepository){
        this.circleRepository = circleRepository;
    }

    public List<Circle> getAllCircles(){
        return circleRepository.findAll();
    }

    public Circle getCircleByName(String circleName){
        return circleRepository.findByName(circleName).orElse(null);
    }

    public Circle createCircle(Circle circle){
        try{
            return circleRepository.save(circle);
        }catch (Exception e){
            System.out.println("ERROR IN SAVING CIRCLE FROM PRE-MADE CIRCLE INSTANCE");
            throw new RuntimeException(e);
        }
    }
}
