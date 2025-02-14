package com.kenanpalmer.super_secret_santa.services;

import com.kenanpalmer.super_secret_santa.Models.Circle;
import com.kenanpalmer.super_secret_santa.Models.User;
import com.kenanpalmer.super_secret_santa.Repositories.CircleRepository;
import com.kenanpalmer.super_secret_santa.dto.CircleDTO;
import com.kenanpalmer.super_secret_santa.dto.CircleRequestDTO;
import com.kenanpalmer.super_secret_santa.dto.UsernameDTO;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CircleService {
    private final CircleRepository circleRepository;
    private final UserService userService;

    public CircleService(CircleRepository circleRepository, UserService userService) {
        this.circleRepository = circleRepository;
        this.userService = userService;
    }

    public List<CircleDTO> getAllCircles(){
        return circleRepository.findAll().stream().map(CircleDTO::new).toList();
    }

    public CircleDTO getCircleByName(String circleName){
        Optional<Circle> circle = circleRepository.findByName(circleName);
        return circle.map(CircleDTO::new).orElse(null);
    }

    public Circle createCircle(CircleRequestDTO circleRequestDTO){
        List<User> users = userService.findAllUsersById(circleRequestDTO.getUsersID());
        Circle circle = new Circle(circleRequestDTO.getName());
        circle.setUsers(new HashSet<>(users));

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
        User user = userService.findUserByUsername(usernameDTO.getUsername());
        Circle circle = getRawCircleByName(circleName);
        circle.addUserToCircle(user);
        try{
            circleRepository.save(circle);
        }catch (Exception e){
            System.out.println("ERROR SAVING CIRCLE AFTER ADDING USER");
        }

        return new CircleDTO(circle);
    }

    private Circle getRawCircleByName(String circleName){
        return circleRepository.findByName(circleName).orElse(null);
    }
}
