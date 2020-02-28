package com.codechallenge.offers.services.serialisation;

import com.codechallenge.offers.config.kafka.events.CreateOfferEvent;
import io.vavr.control.Try;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Deserializer;
import org.springframework.util.SerializationUtils;


public class OfferCreationEventDeserializer implements Deserializer<CreateOfferEvent> {

    @Override
    public CreateOfferEvent deserialize(String topic, byte[] data) {

        return Try.of(() -> (CreateOfferEvent) SerializationUtils.deserialize(data))
                .getOrElseThrow(() -> new RuntimeException("Unable to deserialize event data"));
    }

    @Override
    public CreateOfferEvent deserialize(String topic, Headers headers, byte[] data) {
        return deserialize(topic, data);
    }

}
