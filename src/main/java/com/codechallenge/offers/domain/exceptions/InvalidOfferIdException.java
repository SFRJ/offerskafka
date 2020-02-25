package com.codechallenge.offers.domain.exceptions;

public class InvalidOfferIdException extends RuntimeException {

    public InvalidOfferIdException(String message) {
        super(message);
    }

}
