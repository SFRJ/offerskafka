package com.codechallenge.offers.web;

import com.codechallenge.offers.domain.exceptions.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RequiredArgsConstructor
@RestControllerAdvice
public class ExceptionTranslator {

    private final ModelMapper modelMapper;

    @ExceptionHandler({OfferExpirationException.class, OfferDescriptionException.class, OfferPriceException.class,
            OfferCurrencyException.class, InvalidOfferIdException.class, OfferCreationException.class,
            OfferNotFoundException.class})
    public ResponseEntity<ClientErrorResponse> translate(RuntimeException exception) {

        return new ResponseEntity<>(modelMapper.map(ClientErrorResponse.builder()
                .error(exception.getMessage())
                .build(), ClientErrorResponse.class),
                exception instanceof OfferNotFoundException ? HttpStatus.NOT_FOUND : HttpStatus.BAD_REQUEST);
    }

}
