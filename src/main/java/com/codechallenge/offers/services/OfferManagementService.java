package com.codechallenge.offers.services;

import com.codechallenge.offers.config.kafka.events.CreateOfferEvent;
import com.codechallenge.offers.config.kafka.events.CreateOfferKey;
import com.codechallenge.offers.domain.Offer;
import com.codechallenge.offers.repositories.OffersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.codechallenge.offers.domain.OfferStatus.ACTIVE;
import static java.lang.String.valueOf;

@Slf4j
@Service
@RequiredArgsConstructor
public class OfferManagementService {

    private final OffersRepository offersRepository;

    @KafkaListener(topics = "${kafka.topics.creation}", groupId = "creation", containerFactory = "creationEventsListenerContainerFactory")
    public void listenGroupCreation(@Payload CreateOfferEvent event, @Headers MessageHeaders headers) {

        log.info(valueOf(headers.get("kafka_receivedMessageKey")));
        log.info(valueOf(event));

        offersRepository.createOffer(Offer.builder()
                .identifier(UUID.randomUUID())
                .descriptions(event.getDescription())
                .price(event.getPrice())
                .expirationDate(event.getExpiration())
                .offerStatus(ACTIVE)
                .build());
    }

}
