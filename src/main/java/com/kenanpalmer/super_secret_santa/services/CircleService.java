package com.kenanpalmer.super_secret_santa.services;

import com.kenanpalmer.super_secret_santa.Models.Circle;
import com.kenanpalmer.super_secret_santa.Models.User;
import com.kenanpalmer.super_secret_santa.Repositories.CircleRepository;
import com.kenanpalmer.super_secret_santa.Repositories.UserRepository;
import com.kenanpalmer.super_secret_santa.dto.CircleDTO;
import com.kenanpalmer.super_secret_santa.dto.UsernameDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CircleService {
    private final CircleRepository circleRepository;
    private final UserService userService;

    public CircleService(CircleRepository circleRepository, UserService userService) {
        this.circleRepository = circleRepository;
        this.userService = userService;
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

    public CircleDTO addUserToCircle(String circleName, UsernameDTO usernameDTO){
        System.out.println("THIS IS A CIRCLE NAME FROM SERVICE" + circleName);
        System.out.println("THIS IS A USER FROM SERVICE" + usernameDTO);
        User user = userService.getUserByUsername(usernameDTO.getUsername());
        Circle circle = getCircleByName(circleName);
        circle.addUserToCircle(user);
        try{
            circleRepository.save(circle);
        }catch (Exception e){
            System.out.println("ERROR SAVING CIRCLE AFTER ADDING USER");
        }

        return new CircleDTO(circle);
    }
}
