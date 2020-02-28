package com.codechallenge.offers.config.kafka;

import com.codechallenge.offers.config.kafka.events.CreateOfferEvent;
import com.codechallenge.offers.config.kafka.events.CreateOfferKey;
import com.codechallenge.offers.services.serialisation.OfferCreationEventKeySerializer;
import com.codechallenge.offers.services.serialisation.OfferCreationEventSerializer;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class KafkaProducerConfig {

    @Value(value = "${kafka.bootstrapAddress}")
    private String bootstrapAddress;

    @Bean
    public ProducerFactory<CreateOfferKey, CreateOfferEvent> producerFactory() {

        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, OfferCreationEventKeySerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, OfferCreationEventSerializer.class);

        return new DefaultKafkaProducerFactory<>(configProps, () -> new OfferCreationEventKeySerializer(), () -> new OfferCreationEventSerializer());
    }

    @Bean
    public KafkaTemplate<CreateOfferKey, CreateOfferEvent> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}
