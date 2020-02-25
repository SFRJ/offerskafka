package com.codechallenge.offers.web;

import com.codechallenge.offers.services.OfferCreationEventProducer;
import com.codechallenge.offers.services.OfferValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class OffersController {

    private final OfferCreationEventProducer eventProducer;
    private final OfferValidator offerValidator;

    @Deprecated
    @RequestMapping(value = "/offers/kafka/create", consumes = "application/json", method = RequestMethod.POST)
    public ResponseEntity kafkaCreateOffer(@RequestBody CreateOfferRequest createOfferRequest) {

        offerValidator.validate(createOfferRequest);

        eventProducer.sendMessage(createOfferRequest.getDescription(), createOfferRequest.getPrice(),
                createOfferRequest.getCurrency(), createOfferRequest.getExpiration());

        return ResponseEntity.ok().build();
    }
}
