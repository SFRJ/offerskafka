package com.codechallenge.offers.services;

import com.codechallenge.offers.domain.exceptions.*;
import com.codechallenge.offers.web.CreateOfferRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class OfferValidator {

    public void validate(CreateOfferRequest request) {

        if (request == null) {
            throw new OfferExpirationException("Can't create offer: Missing request body");
        }

        if (request.getExpiration() == null) {
            throw new OfferExpirationException("Can't create offer: An expiration date needs to be provided");
        }

        if (request.getExpiration().isBefore(LocalDate.now())) {
            throw new OfferExpirationException("Can't create offer: The expiration time has to be a date in the future");
        }

        if (request.getPrice() == null) {
            throw new OfferPriceException("Can't create offer: A price needs to be provided");
        }

        if (request.getPrice() < 0) {
            throw new OfferPriceException("Can't create offer: A negative price cant't be provided");
        }

        if (request.getCurrency() == null || request.getCurrency().isEmpty()) {
            throw new OfferCurrencyException("Can't create offer: A currency needs to be provided");
        }

        if (request.getDescription() == null || request.getDescription().isEmpty()) {
            throw new OfferDescriptionException("Can't create offer: A description needs to be provided");
        }
    }

    public void validate(UUID offerId) {
        if (offerId == null) {
            throw new InvalidOfferIdException("The offer id cannot be null");
        }
    }
}
