package com.kenanpalmer.super_secret_santa.converter;

import com.kenanpalmer.super_secret_santa.dto.CircleRequestDTO;
import com.kenanpalmer.super_secret_santa.models.Circle;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CircleRequestDTOToCIrcleConverter
        implements Converter<CircleRequestDTO, Circle> {


    @Override
    public Circle convert(CircleRequestDTO source) {
        return null;
    }
}
