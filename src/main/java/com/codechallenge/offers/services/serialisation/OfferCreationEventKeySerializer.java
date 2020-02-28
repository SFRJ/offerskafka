package com.codechallenge.offers.services.serialisation;

import com.codechallenge.offers.config.kafka.events.CreateOfferKey;
import lombok.NoArgsConstructor;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Serializer;
import org.springframework.util.SerializationUtils;


@NoArgsConstructor
public class OfferCreationEventKeySerializer implements Serializer<CreateOfferKey> {

    @Override
    public byte[] serialize(String topic, CreateOfferKey data) {
        return SerializationUtils.serialize(data);
    }

    @Override
    public byte[] serialize(String topic, Headers headers, CreateOfferKey data) {
        return serialize(topic, data);
    }
}
