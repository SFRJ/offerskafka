package com.codechallenge.offers.services.serialisation;

import com.codechallenge.offers.config.kafka.events.CreateOfferKey;
import io.vavr.control.Try;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Deserializer;
import org.springframework.util.SerializationUtils;

public class OfferCreationEventKeyDeserializer implements Deserializer<CreateOfferKey> {

    @Override
    public CreateOfferKey deserialize(String topic, byte[] data) {

        return Try.of(() -> (CreateOfferKey) SerializationUtils.deserialize(data))
                .getOrElseThrow(() -> new RuntimeException("Unable to deserialize event key"));
    }

    @Override
    public CreateOfferKey deserialize(String topic, Headers headers, byte[] data) {
        return deserialize(topic, data);
    }

}
