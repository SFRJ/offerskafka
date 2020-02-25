package com.codechallenge.offers.config.kafka.events;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@AllArgsConstructor
@Data
public class CreateOfferKey implements Serializable {

    private final UUID offerCreationKey;

}
