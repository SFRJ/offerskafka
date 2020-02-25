package com.codechallenge.offers.web;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@AllArgsConstructor
@Data
public class CreateOfferRequest {

    private final String description;
    private final Double price;
    private final String currency;
    private final LocalDate expiration;
}
