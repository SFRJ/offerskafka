package com.codechallenge.offers.services.serialisation;

import io.vavr.control.Try;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Deserializer;
import org.springframework.util.SerializationUtils;

import java.util.Map;

public class OfferCreationEventDeserializer implements Deserializer {

    @Override
    public void configure(Map configs, boolean isKey) {

    }

    @Override
    public Object deserialize(String topic, byte[] data) {

        return Try.of(() -> SerializationUtils.deserialize(data))
                .getOrElseThrow(() -> new RuntimeException("Unable to deserialize event data"));
    }

    @Override
    public Object deserialize(String topic, Headers headers, byte[] data) {
        return null;
    }

    @Override
    public void close() {

    }
}
