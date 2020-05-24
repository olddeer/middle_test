package com.spintech.testtask.config;

import com.spintech.testtask.service.converter.TvDtoToTvEntity;
import com.spintech.testtask.service.converter.TvEntityToTvDto;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new TvEntityToTvDto());
        registry.addConverter(new TvDtoToTvEntity());
    }
}