package com.codechallenge.offers.services;

import com.codechallenge.offers.domain.Offer;
import com.codechallenge.offers.domain.OfferStatus;
import com.codechallenge.offers.repositories.OffersRepository;
import io.vavr.control.Option;
import io.vavr.control.Try;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class OfferManagementServiceTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private OffersRepository offersRepository;
    private OfferManagementService offerManagementService;

    @Before
    public void setUp() {

        offersRepository = Mockito.mock(OffersRepository.class);
        offerManagementService = new OfferManagementService(offersRepository);
    }

    @Test
    public void shouldCreateOffer() {

        UUID someIdentifier = UUID.randomUUID();
        Mockito.when(offersRepository.createOffer(Mockito.any())).thenReturn(Try.of(() -> someIdentifier));
        Try<UUID> offerId = offerManagementService.createOffer(null, 0D, null);

        Mockito.verify(offersRepository).createOffer(Mockito.any());
        assertThat(offerId.get()).isEqualTo(someIdentifier);
    }

    @Test
    public void shouldGetCancelledOffer() {

        UUID someIdentifier = UUID.randomUUID();
        Offer offer = Offer.builder()
                .expirationDate(LocalDate.now().minusDays(1))
                .offerStatus(OfferStatus.ACTIVE)
                .identifier(someIdentifier)
                .build();
        Mockito.when(offersRepository.readOffer(someIdentifier)).thenReturn(Try.of(() -> Option.of(offer)));
        Mockito.when(offersRepository.cancelOffer(someIdentifier)).thenReturn(Try.of(() -> {
            offer.setOfferStatus(OfferStatus.CANCELLED);
            return Option.of(offer);
        }));

        Try<Offer> result = offerManagementService.getOffer(someIdentifier);
        assertThat(result.get().getIdentifier()).isEqualTo(someIdentifier);
        assertThat(result.get().getOfferStatus()).isEqualTo(OfferStatus.CANCELLED);
    }

    @Test
    public void shouldGetActiveOffer() {

        UUID someIdentifier = UUID.randomUUID();
        Offer offer = Offer.builder()
                .expirationDate(LocalDate.now().plusDays(1))
                .identifier(someIdentifier)
                .offerStatus(OfferStatus.ACTIVE)
                .build();
        Mockito.when(offersRepository.readOffer(someIdentifier)).thenReturn(Try.of(() -> Option.of(offer)));

        Try<Offer> result = offerManagementService.getOffer(someIdentifier);
        assertThat(result.get().getIdentifier()).isEqualTo(someIdentifier);
        assertThat(result.get().getOfferStatus()).isEqualTo(OfferStatus.ACTIVE);
    }

    @Test
    public void shouldCancelOffer() {

        UUID offerId = UUID.randomUUID();
        Mockito.when(offersRepository.cancelOffer(offerId)).thenReturn(Try.success(Option.of(Offer.builder().build())));

        offerManagementService.cancelOffer(offerId);

        Mockito.verify(offersRepository).cancelOffer(offerId);
    }
}
