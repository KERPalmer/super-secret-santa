package com.kenanpalmer.super_secret_santa.services;

import com.kenanpalmer.super_secret_santa.converter.CircleRequestDTOToCircleConverter;
import com.kenanpalmer.super_secret_santa.converter.CircleToCircleResponseDTOConverter;
import com.kenanpalmer.super_secret_santa.dto.CircleRequestDTO;
import com.kenanpalmer.super_secret_santa.dto.CircleResponseDTO;
import com.kenanpalmer.super_secret_santa.exception.CircleConversionException;
import com.kenanpalmer.super_secret_santa.models.Circle;
import com.kenanpalmer.super_secret_santa.repositories.CircleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CircleService {

    private final CircleRequestDTOToCircleConverter circleRequestDTOToCircleConverter;
    private final CircleToCircleResponseDTOConverter circleToCircleResponseDTOConverter;
    private final CircleRepository circleRepository;

    public CircleService(CircleRequestDTOToCircleConverter circleRequestDTOToCircleConverter, CircleToCircleResponseDTOConverter circleToCircleResponseDTOConverter, CircleRepository circleRepository, UserService userService) {
        this.circleRequestDTOToCircleConverter = circleRequestDTOToCircleConverter;
        this.circleToCircleResponseDTOConverter = circleToCircleResponseDTOConverter;
        this.circleRepository = circleRepository;
    }

    public List<CircleResponseDTO> getAllCircles() {
        return circleRepository.findAll().stream().map(circleToCircleResponseDTOConverter::convert).toList();
    }

    public List<CircleResponseDTO> getCirclesByUsername(String username){
        return circleRepository.findAll().stream()
            .filter(circle -> circle.getUsers().stream()
                    .anyMatch(user -> user.getUsername().equals(username)))
            .map(circleToCircleResponseDTOConverter::convert)
            .toList();
    }

    public CircleResponseDTO createCircle(CircleRequestDTO circleRequestDTO) {
        Circle circle = circleRequestDTOToCircleConverter.convert(circleRequestDTO);
        if(circle == null) throw new CircleConversionException("Converted Circle is null");
        return circleToCircleResponseDTOConverter.convert(circleRepository.save(circle));
    }
}
