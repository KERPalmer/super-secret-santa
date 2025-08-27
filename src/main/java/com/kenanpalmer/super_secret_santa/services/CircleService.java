package com.kenanpalmer.super_secret_santa.services;

import com.kenanpalmer.super_secret_santa.converter.CircleRequestDTOToCircleConverter;
import com.kenanpalmer.super_secret_santa.converter.CircleToCircleResponseDTOConverter;
import com.kenanpalmer.super_secret_santa.dto.CircleDTO;
import com.kenanpalmer.super_secret_santa.dto.CircleRequestDTO;
import com.kenanpalmer.super_secret_santa.dto.CircleResponseDTO;
import com.kenanpalmer.super_secret_santa.dto.user.UsernameDTO;
import com.kenanpalmer.super_secret_santa.exception.CircleConversionException;
import com.kenanpalmer.super_secret_santa.exception.CircleNotFoundException;
import com.kenanpalmer.super_secret_santa.exception.CircleSaveException;
import com.kenanpalmer.super_secret_santa.exception.UsernameNotFoundException;
import com.kenanpalmer.super_secret_santa.models.Circle;
import com.kenanpalmer.super_secret_santa.models.User;
import com.kenanpalmer.super_secret_santa.repositories.CircleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CircleService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CircleService.class);

    private final CircleRequestDTOToCircleConverter circleRequestDTOToCircleConverter;
    private final CircleToCircleResponseDTOConverter circleToCircleResponseDTOConverter;
    private final CircleRepository circleRepository;
    private final UserService userService;

    public CircleService(CircleRequestDTOToCircleConverter circleRequestDTOToCircleConverter, CircleToCircleResponseDTOConverter circleToCircleResponseDTOConverter, CircleRepository circleRepository, UserService userService) {
        this.circleRequestDTOToCircleConverter = circleRequestDTOToCircleConverter;
        this.circleToCircleResponseDTOConverter = circleToCircleResponseDTOConverter;
        this.circleRepository = circleRepository;
        this.userService = userService;
    }

    public List<CircleResponseDTO> getAllCircles() {
        return circleRepository.findAll().stream().map(circleToCircleResponseDTOConverter::convert).toList();
    }

    public CircleDTO getCircleByName(String circleName) {
        Optional<Circle> circle = circleRepository.findByName(circleName);
        return circle.map(CircleDTO::new).orElse(null);
    }

    public List<CircleResponseDTO> getCirclesByUsername(String username){
        return circleRepository.findAll().stream()
            .filter(circle -> circle.getUsers().stream()
                    .anyMatch(user -> user.getUsername().equals(username)))
            .map(circleToCircleResponseDTOConverter::convert)
            .toList();
    }

    public Circle createCircle(CircleRequestDTO circleRequestDTO) {
        Circle circle = circleRequestDTOToCircleConverter.convert(circleRequestDTO);
        if(circle == null) throw new CircleConversionException("Converted Circle is null");
        return circleRepository.save(circle);
    }
}
