package com.kenanpalmer.super_secret_santa.converter;

import com.kenanpalmer.super_secret_santa.dto.CircleRequestDTO;
import com.kenanpalmer.super_secret_santa.exception.CircleConversionException;
import com.kenanpalmer.super_secret_santa.models.Circle;
import com.kenanpalmer.super_secret_santa.models.User;
import com.kenanpalmer.super_secret_santa.services.UserService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
        try {
            Circle circle = new Circle();
            circle.setName(source.getName());
            circle.setDescription(source.getDescription());
            circle.setActive(true);
            circle.setUsers(
                    source.getUsersID() == null || source.getUsersID().isEmpty()
                            ? new HashSet<>()
                            : new HashSet<>(userService.findAllUsersByIDs(source.getUsersID()))
            );

            //set owner as current logged-in user
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String username = auth.getName(); // default if UserDetailsService used
            User owner = userService.findUserByUsername(username);
            circle.setOwner(owner);
            return circle;
        }
        catch (Exception e){
            throw new CircleConversionException(e);
        }
    }
}
