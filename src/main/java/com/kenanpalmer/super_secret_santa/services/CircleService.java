package com.kenanpalmer.super_secret_santa.services;

import com.kenanpalmer.super_secret_santa.dto.CircleDTO;
import com.kenanpalmer.super_secret_santa.dto.CircleRequestDTO;
import com.kenanpalmer.super_secret_santa.dto.user.UsernameDTO;
import com.kenanpalmer.super_secret_santa.models.Circle;
import com.kenanpalmer.super_secret_santa.models.User;
import com.kenanpalmer.super_secret_santa.repositories.CircleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class CircleService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CircleService.class);

    private final CircleRepository circleRepository;
    private final UserService userService;

    public CircleService(CircleRepository circleRepository, UserService userService) {
        this.circleRepository = circleRepository;
        this.userService = userService;
    }

    public List<CircleDTO> getAllCircles() {
        return circleRepository.findAll().stream().map(CircleDTO::new).toList();
    }

    public CircleDTO getCircleByName(String circleName) {
        Optional<Circle> circle = circleRepository.findByName(circleName);
        return circle.map(CircleDTO::new).orElse(null);
    }

    public Circle createCircle(CircleRequestDTO circleRequestDTO) {
        List<User> users = userService.findAllUsersById(circleRequestDTO.getUsersID());
        User owner = userService.findUserByID(circleRequestDTO.getOwner());
        Circle circle = new Circle(circleRequestDTO.getName(), owner);
        circle.setUsers(new HashSet<>(users));

        try {
            return circleRepository.save(circle);
        } catch (Exception e) {
            LOGGER.info("ERROR IN SAVING CIRCLE FROM PRE-MADE CIRCLE INSTANCE");
            throw new RuntimeException(e);
        }
    }

    public CircleDTO addUserToCircle(String circleName, UsernameDTO usernameDTO) {
        LOGGER.info("THIS IS A CIRCLE NAME FROM SERVICE {}", circleName);
        LOGGER.info("THIS IS A USER FROM SERVICE {}", usernameDTO);
        User user = userService.findUserByUsername(usernameDTO.getUsername());
        Circle circle = getRawCircleByName(circleName);
        circle.addUserToCircle(user);
        try {
            circleRepository.save(circle);
        } catch (Exception e) {
            System.out.println("ERROR SAVING CIRCLE AFTER ADDING USER");
        }

        return new CircleDTO(circle);
    }

    private Circle getRawCircleByName(String circleName) {
        return circleRepository.findByName(circleName).orElse(null);
    }
}
