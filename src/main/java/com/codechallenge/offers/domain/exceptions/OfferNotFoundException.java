package com.codechallenge.offers.domain.exceptions;

public class OfferNotFoundException extends RuntimeException {

    public OfferNotFoundException(String message) {
        super(message);
    }
}
