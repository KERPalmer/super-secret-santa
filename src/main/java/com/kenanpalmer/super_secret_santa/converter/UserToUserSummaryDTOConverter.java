package com.kenanpalmer.super_secret_santa.converter;


import com.kenanpalmer.super_secret_santa.Models.User;
import com.kenanpalmer.super_secret_santa.dto.UserSummaryDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserToUserSummaryDTOConverter implements Converter<User, UserSummaryDTO> {

    @Override
    public UserSummaryDTO convert(User user){
        return new UserSummaryDTO(user.getId(), user.getUsername());
    }

}
