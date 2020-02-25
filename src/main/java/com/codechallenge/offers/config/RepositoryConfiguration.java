package com.codechallenge.offers.config;

import com.codechallenge.offers.repositories.InMemoryOffersRepository;
import com.codechallenge.offers.repositories.OffersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Configuration
@RequiredArgsConstructor
public class RepositoryConfiguration {

    @Bean
    OffersRepository offersRepository() {
        return new InMemoryOffersRepository(new HashMap<>());
    }

}
