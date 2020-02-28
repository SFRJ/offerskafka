package com.codechallenge.offers.services;

import com.codechallenge.offers.config.kafka.events.CreateOfferEvent;
import com.codechallenge.offers.config.kafka.events.CreateOfferKey;
import com.codechallenge.offers.domain.Offer;
import com.codechallenge.offers.repositories.OffersRepository;
import io.vavr.Value;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

import static com.codechallenge.offers.domain.OfferStatus.ACTIVE;

@Service
@RequiredArgsConstructor
public class OfferManagementService {

    private final OffersRepository offersRepository;

    public Try<UUID> createOffer(String description, Double price, LocalDate expiration) {

        return offersRepository.createOffer(Offer.builder()
                .identifier(UUID.randomUUID())
                .descriptions(description)
                .price(price)
                .expirationDate(expiration)
                .offerStatus(ACTIVE)
                .build());
    }

    @KafkaListener(topics = "${kafka.topics.creation}", groupId = "creation", containerFactory = "creationEventsListenerContainerFactory")
    public void listenGroupCreation(CreateOfferEvent event) {
        System.out.println(String.format("Description %s", event.getDescription()));
        System.out.println(String.format("Currency %s", event.getCurrency()));
        System.out.println(String.format("Expiration %s", event.getExpiration()));

        createOffer(event.getDescription(), event.getPrice(), event.getExpiration());
    }

}
