package com.kenanpalmer.super_secret_santa.converter;

import com.kenanpalmer.super_secret_santa.dto.CircleRequestDTO;
import com.kenanpalmer.super_secret_santa.models.Circle;
import com.kenanpalmer.super_secret_santa.services.UserService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
public class CircleRequestDTOToCircleConverter
        implements Converter<CircleRequestDTO, Circle> {

    private final UserService userService;

    public CircleRequestDTOToCircleConverter(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Circle convert(CircleRequestDTO source) {
        Circle circle = new Circle();
        circle.setName(source.getName());
        circle.setDescription(source.getDescription());
        circle.setActive(true);
        circle.setUsers(
                new HashSet<>(userService.findUsersByIDs(source.getUsersID()))
        );
        return circle;
    }
}
