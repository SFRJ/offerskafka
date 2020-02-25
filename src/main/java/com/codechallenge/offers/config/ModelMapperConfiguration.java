package com.codechallenge.offers.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ModelMapperConfiguration {

    private final ObjectMapper objectMapper;

    @Bean
    ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @PostConstruct
    public void init() {
        objectMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
    }
}
