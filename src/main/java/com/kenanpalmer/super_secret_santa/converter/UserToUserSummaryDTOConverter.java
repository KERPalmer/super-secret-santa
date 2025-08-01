package com.kenanpalmer.super_secret_santa.converter;


import com.kenanpalmer.super_secret_santa.dto.UserSummaryDTO;
import com.kenanpalmer.super_secret_santa.models.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserToUserSummaryDTOConverter implements Converter<User, UserSummaryDTO> {

    @Override
    public UserSummaryDTO convert(User source) {
        return new UserSummaryDTO(source.getId(), source.getUsername());
    }

}
