package com.codechallenge.offers.services;

import com.codechallenge.offers.domain.exceptions.*;
import com.codechallenge.offers.web.CreateOfferRequest;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.time.LocalDate;
import java.util.UUID;

public class OfferValidatorTest {

    private static final String SOME_DESCRIPTION = "someDescription";
    private static final Double SOME_PRICE = 10D;
    private static final String SOME_CURRENCY = "someCurrency";
    private static final LocalDate DATE_IN_PAST = LocalDate.now().minusDays(1);
    private static final LocalDate DATE_IN_FUTURE = LocalDate.now().plusDays(1);

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private OfferValidator offerValidator;


    @Before
    public void setUp() {
        offerValidator = new OfferValidator();
    }

    @Test
    public void shouldFailValidationForMissingExpirationDate() {

        CreateOfferRequest request = new CreateOfferRequest(SOME_DESCRIPTION, SOME_PRICE, SOME_CURRENCY, null);
        expectedException.expect(OfferExpirationException.class);
        expectedException.expectMessage("Can't create offer: An expiration date needs to be provided");

        offerValidator.validate(request);
    }

    @Test
    public void shouldFailValidationForWrongExpirationDate() {

        CreateOfferRequest request = new CreateOfferRequest(SOME_DESCRIPTION, SOME_PRICE, SOME_CURRENCY, DATE_IN_PAST);
        expectedException.expect(OfferExpirationException.class);
        expectedException.expectMessage("Can't create offer: The expiration time has to be a date in the future");

        offerValidator.validate(request);
    }

    @Test
    public void shouldFailValidationForMissingPrice() {

        CreateOfferRequest request = new CreateOfferRequest(SOME_DESCRIPTION, null, SOME_CURRENCY, DATE_IN_FUTURE);
        expectedException.expect(OfferPriceException.class);
        expectedException.expectMessage("Can't create offer: A price needs to be provided");

        offerValidator.validate(request);
    }

    @Test
    public void shouldFailValidationForWrongPrice() {

        CreateOfferRequest request = new CreateOfferRequest(SOME_DESCRIPTION, -1D, SOME_CURRENCY, DATE_IN_FUTURE);
        expectedException.expect(OfferPriceException.class);
        expectedException.expectMessage("Can't create offer: A negative price cant't be provided");

        offerValidator.validate(request);
    }

    @Test
    public void shouldFailValidationForMissingCurrency() {

        CreateOfferRequest request = new CreateOfferRequest(SOME_DESCRIPTION, SOME_PRICE, null, DATE_IN_FUTURE);
        expectedException.expect(OfferCurrencyException.class);
        expectedException.expectMessage("Can't create offer: A currency needs to be provided");

        offerValidator.validate(request);
    }

    @Test
    public void shouldFailValidationForEmptyCurrency() {

        CreateOfferRequest request = new CreateOfferRequest(SOME_DESCRIPTION, SOME_PRICE, "", DATE_IN_FUTURE);
        expectedException.expect(OfferCurrencyException.class);
        expectedException.expectMessage("Can't create offer: A currency needs to be provided");

        offerValidator.validate(request);
    }

    @Test
    public void shouldFailValidationForMissingDescription() {

        CreateOfferRequest request = new CreateOfferRequest(null, SOME_PRICE, SOME_CURRENCY, DATE_IN_FUTURE);
        expectedException.expect(OfferDescriptionException.class);
        expectedException.expectMessage("Can't create offer: A description needs to be provided");

        offerValidator.validate(request);
    }

    @Test
    public void shouldFailValidationForEmptyDescription() {

        CreateOfferRequest request = new CreateOfferRequest("", SOME_PRICE, SOME_CURRENCY, DATE_IN_FUTURE);
        expectedException.expect(OfferDescriptionException.class);
        expectedException.expectMessage("Can't create offer: A description needs to be provided");

        offerValidator.validate(request);
    }

    @Test
    public void shouldFailValidationForMissingRequest() {

        expectedException.expect(OfferExpirationException.class);
        expectedException.expectMessage("Can't create offer: Missing request body");

        CreateOfferRequest request = null;
        offerValidator.validate(request);
    }

    @Test
    public void shouldFailValidationForMissingOfferId() {

        expectedException.expect(InvalidOfferIdException.class);
        expectedException.expectMessage("The offer id cannot be null");

        UUID offerId = null;
        offerValidator.validate(offerId);
    }
}