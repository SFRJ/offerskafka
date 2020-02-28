package com.codechallenge.offers.services.serialisation;

import com.codechallenge.offers.config.kafka.events.CreateOfferEvent;
import lombok.NoArgsConstructor;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Serializer;
import org.springframework.util.SerializationUtils;

@NoArgsConstructor
public class OfferCreationEventSerializer implements Serializer<CreateOfferEvent> {


    @Override
    public byte[] serialize(String topic, CreateOfferEvent data) {
        return SerializationUtils.serialize(data);
    }

    @Override
    public byte[] serialize(String topic, Headers headers, CreateOfferEvent data) {

        return serialize(topic, data);
    }
}
