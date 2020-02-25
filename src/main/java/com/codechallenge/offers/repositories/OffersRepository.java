package com.codechallenge.offers.repositories;

import com.codechallenge.offers.domain.Offer;
import io.vavr.control.Option;
import io.vavr.control.Try;

import java.util.Optional;
import java.util.UUID;

public interface OffersRepository {

    Try<UUID> createOffer(Offer offer);
    Try<Option<Offer>> readOffer(UUID uuid);
    Try<Option<Offer>> cancelOffer(UUID uuid);

}
