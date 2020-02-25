package com.codechallenge.offers.repositories;

import com.codechallenge.offers.domain.Offer;
import io.vavr.control.Option;
import io.vavr.control.Try;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class InMemoryOffersRepositoryTest {

    private Map database;
    private InMemoryOffersRepository inMemoryOffersRepository;

    @Before
    public void setUp() {
        database = Mockito.mock(Map.class);
        inMemoryOffersRepository = new InMemoryOffersRepository(database);
    }

    @Test
    public void shouldInsertInDatabase() {

        Offer someOffer = Offer.builder().identifier(UUID.randomUUID()).build();
        Try<UUID> offerId = inMemoryOffersRepository.createOffer(someOffer);

        Mockito.verify(database).put(someOffer.getIdentifier(), someOffer);
        assertThat(someOffer.getIdentifier()).isEqualTo(offerId.get());
    }

    @Test
    public void shouldReadOffer() {

        UUID offerId = UUID.randomUUID();
        Mockito.when(database.get(offerId)).thenReturn(Offer.builder().identifier(offerId).build());

        Try<Option<Offer>> offer = inMemoryOffersRepository.readOffer(offerId);

        assertThat(offer.get().get().getIdentifier()).isEqualTo(offerId);
    }

}