package com.codechallenge.offers.config.kafka.events;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@AllArgsConstructor
@Data
public class CreateOfferKey {

    private UUID offerCreationKey;

}
