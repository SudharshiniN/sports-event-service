package com.sports.event.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sports.event.externalEvent.MockEventApiResponse;
import org.springframework.stereotype.Component;

import java.util.function.Function;

/**
 * Converts the external api response to a message that needs to be published to kafka topic
 */
@Component
public class KafkaMessageTransformer implements Function<MockEventApiResponse, String> {

    private final ObjectMapper objectMapper;

    public KafkaMessageTransformer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public String apply(MockEventApiResponse mockEventApiResponse) {
        try {
            return objectMapper.writeValueAsString(mockEventApiResponse);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert apiresponse to kafka message", e);
        }
    }
}
