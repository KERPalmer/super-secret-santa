package com.kenanpalmer.super_secret_santa.converter;

import com.kenanpalmer.super_secret_santa.dto.CircleResponseDTO;
import com.kenanpalmer.super_secret_santa.exception.CircleConversionException;
import com.kenanpalmer.super_secret_santa.models.Circle;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CircleToCircleResponseDTOConverter
        implements Converter<Circle, CircleResponseDTO> {

    private final UserToUserSummaryDTOConverter userToUserSummaryDTOConverter;

    public CircleToCircleResponseDTOConverter(
            UserToUserSummaryDTOConverter userToUserSummaryDTOConverter) {
        this.userToUserSummaryDTOConverter = userToUserSummaryDTOConverter;
    }

    @Override
    public CircleResponseDTO convert(Circle source) {
        try {
            CircleResponseDTO responseDTO = new CircleResponseDTO();
            responseDTO.setId(source.getId());
            responseDTO.setName(source.getName());
            responseDTO.setDescription(source.getDescription());
            responseDTO.setActive(source.getActive());
            responseDTO.setUsers(source.getUsers().parallelStream()
                    .map(userToUserSummaryDTOConverter::convert)
                    .toList());
            responseDTO.setOwnerID(source.getOwner().getId());
            return responseDTO;
        }
        catch (Exception e){
            throw new CircleConversionException(e);
        }
    }
}
