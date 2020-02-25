package com.codechallenge.offers.domain.exceptions;

public class OfferExpirationException extends RuntimeException {

    public OfferExpirationException(String message) {
        super(message);
    }
}
