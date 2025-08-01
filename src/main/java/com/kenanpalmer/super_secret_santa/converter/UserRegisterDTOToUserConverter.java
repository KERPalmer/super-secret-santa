package com.kenanpalmer.super_secret_santa.converter;

import com.kenanpalmer.super_secret_santa.dto.user.UserRegisterDTO;
import com.kenanpalmer.super_secret_santa.models.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserRegisterDTOToUserConverter implements Converter<UserRegisterDTO, User> {


    @Override
    public User convert(UserRegisterDTO source) {
        return new User(source.getUsername(), source.getPassword());
    }
}
