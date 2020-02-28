package com.codechallenge.offers.services;

import com.codechallenge.offers.config.kafka.events.CreateOfferEvent;
import com.codechallenge.offers.config.kafka.events.CreateOfferKey;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OfferCreationEventProducer {

    private final KafkaTemplate<CreateOfferKey, CreateOfferEvent> kafkaTemplate;

    @Value(value = "${kafka.topics.creation}")
    private String topicName;

    public void sendMessage(String description, Double price, String currency, LocalDate expiration) {

        CreateOfferKey key = new CreateOfferKey(UUID.randomUUID());

        CreateOfferEvent event = new CreateOfferEvent(description, price, currency, expiration);

        ListenableFuture<SendResult<CreateOfferKey, CreateOfferEvent>> future = kafkaTemplate.send(topicName, key, event);
        kafkaTemplate.flush();

        future.addCallback(new ListenableFutureCallback<SendResult<CreateOfferKey, CreateOfferEvent>>() {
            @Override
            public void onFailure(Throwable ex) {
                System.out.println("Unable to send event to Kafka:" + ex);
            }

            @Override
            public void onSuccess(SendResult<CreateOfferKey, CreateOfferEvent> result) {
                System.out.println(String.format("Sent creation event ", result.getProducerRecord().value()));
            }
        });
    }


}
