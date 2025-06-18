package com.insurance.mvp.config;

import com.insurance.mvp.dto.CollateralResponse;
import com.insurance.mvp.entity.CollateralEntity;
import com.insurance.mvp.util.DateConverterUtil;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        Converter<LocalDateTime, String> localDateTimeToJalaliString = ctx ->
                ctx.getSource() == null ? null : DateConverterUtil.toJalaliDate(ctx.getSource().toLocalDate());

        modelMapper.typeMap(CollateralEntity.class, CollateralResponse.class)
                .addMappings(mapper -> {
                    mapper.using(localDateTimeToJalaliString).map(CollateralEntity::getBeginDate, CollateralResponse::setBeginDate);
                    mapper.using(localDateTimeToJalaliString).map(CollateralEntity::getEndDate, CollateralResponse::setEndDate);
                    mapper.using(localDateTimeToJalaliString).map(CollateralEntity::getCancellationDate, CollateralResponse::setCancellationDate);
                    mapper.using(localDateTimeToJalaliString).map(CollateralEntity::getConfirmationDate, CollateralResponse::setConfirmationDate);
                });

        return modelMapper;
    }
}
