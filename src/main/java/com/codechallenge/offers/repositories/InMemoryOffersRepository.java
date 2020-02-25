package com.codechallenge.offers.repositories;

import com.codechallenge.offers.domain.Offer;
import com.codechallenge.offers.domain.OfferStatus;
import io.vavr.control.Option;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class InMemoryOffersRepository implements OffersRepository {

    private final Map<UUID, Offer> offers;

    @Override
    public Try<UUID> createOffer(Offer offer) {

        offers.put(offer.getIdentifier(), offer);
        return Try.of(offer::getIdentifier);
    }

    @Override
    public Try<Option<Offer>> readOffer(UUID uuid) {

        return Try.of(() -> Option.of(offers.get(uuid)));
    }

    @Override
    public Try<Option<Offer>> cancelOffer(UUID uuid) {

        return Try.of(() -> Option.of(offers.get(uuid)).map(offer -> {
             offer.setOfferStatus(OfferStatus.CANCELLED);
             offers.replace(uuid, offer);
             return offer;
         }));
    }

}
