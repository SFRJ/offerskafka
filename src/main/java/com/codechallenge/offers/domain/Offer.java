package com.codechallenge.offers.domain;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Builder
@Data
public class Offer {

    private final UUID identifier;
    private final String descriptions;
    private final Double price;
    private final LocalDate expirationDate;
    private OfferStatus offerStatus;

}
