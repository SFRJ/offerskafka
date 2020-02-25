package com.codechallenge.offers.config.kafka.events;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@AllArgsConstructor
@Data
public class CreateOfferEvent {

    private final String description;
    private final Double price;
    private final String currency;
    private final LocalDate expiration;

}
