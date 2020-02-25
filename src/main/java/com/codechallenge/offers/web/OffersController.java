package com.codechallenge.offers.web;

import com.codechallenge.offers.config.kafka.events.CreateOfferEvent;
import com.codechallenge.offers.config.kafka.events.CreateOfferKey;
import com.codechallenge.offers.domain.Offer;
import com.codechallenge.offers.domain.exceptions.OfferCancelationException;
import com.codechallenge.offers.domain.exceptions.OfferCreationException;
import com.codechallenge.offers.domain.exceptions.OfferNotFoundException;
import com.codechallenge.offers.services.OfferValidator;
import com.codechallenge.offers.services.OfferManagementService;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class OffersController {

    private final KafkaTemplate<CreateOfferKey, CreateOfferEvent> kafkaTemplate;
    private final OfferManagementService offerManagementService;
    private final OfferValidator offerValidator;
    private final ModelMapper modelMapper;

    @RequestMapping(value = "/offers/create", consumes = "application/json", method = RequestMethod.POST)
    public ResponseEntity<UUID> createOffer(@RequestBody CreateOfferRequest createOfferRequest) {

        offerValidator.validate(createOfferRequest);

        return ResponseEntity.ok(offerManagementService
                .createOffer(createOfferRequest.getDescription(), createOfferRequest.getPrice(),
                        createOfferRequest.getExpiration())
                .getOrElseThrow(() -> new OfferCreationException("Unable to create offer")));
    }

    @RequestMapping(value = "/offers/{offerId}", produces = "application/json", method = RequestMethod.GET)
    public ResponseEntity<Object> getOffer(@PathVariable("offerId") UUID offerId) {

        offerValidator.validate(offerId);

        return ResponseEntity.ok(modelMapper.map(offerManagementService.getOffer(offerId)
                .getOrElseThrow(() -> new OfferNotFoundException("Unable to find offer with id " + offerId)), Offer.class));
    }

    @RequestMapping(value = "/offers/{offerId}/cancel", method = RequestMethod.GET)
    public ResponseEntity<Offer> cancelOffer(@PathVariable("offerId") UUID offerId) {

        offerValidator.validate(offerId);

        return ResponseEntity.ok(modelMapper.map(offerManagementService.cancelOffer(offerId)
                .getOrElseThrow(() -> new OfferCancelationException("Unable to cancel offer")), Offer.class));
    }

    @RequestMapping(value = "/offers/kafka/create", consumes = "application/json", method = RequestMethod.POST)
    public ResponseEntity<UUID> kafkaCreateOffer(@RequestBody CreateOfferRequest createOfferRequest) {

        offerValidator.validate(createOfferRequest);

        CreateOfferKey key = new CreateOfferKey(UUID.randomUUID());

        CreateOfferEvent event = new CreateOfferEvent(createOfferRequest.getDescription(), createOfferRequest.getPrice(),
                createOfferRequest.getCurrency(), createOfferRequest.getExpiration());

        ListenableFuture<SendResult<CreateOfferKey, CreateOfferEvent>> future = kafkaTemplate.send("creation", key, event);

        future.addCallback(new ListenableFutureCallback<SendResult<CreateOfferKey, CreateOfferEvent>>() {
            @Override
            public void onFailure(Throwable ex) {
                System.out.println("Unable to send event to Kafka");
            }

            @Override
            public void onSuccess(SendResult<CreateOfferKey, CreateOfferEvent> result) {
                System.out.println(String.format("Sent creation event ", result.getProducerRecord().value()));
            }
        });

        return ResponseEntity.ok(key.getOfferCreationKey());
    }
}
