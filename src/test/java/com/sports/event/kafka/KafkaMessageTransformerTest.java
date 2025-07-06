package com.sports.event.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sports.event.externalEvent.MockEventApiResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class KafkaMessageTransformerTest {

    private KafkaMessageTransformer transformer;

    ObjectMapper mockMapper;

    @Test
    public void testApplyValidInput_returnsJsonString() {
        transformer = new KafkaMessageTransformer(new ObjectMapper());

        MockEventApiResponse response = new MockEventApiResponse();
        response.setEventId("event123");
        response.setCurrentScore("1:1");
        response.setScoreTimeStamp(1234567890L);

        String json = transformer.apply(response);

        assertNotNull(json);
        assertTrue(json.contains("\"eventId\":\"event123\""));
        assertTrue(json.contains("\"currentScore\":\"1:1\""));
        assertTrue(json.contains("\"scoreTimeStamp\":1234567890"));
    }

    @Test
    public void testApplyNullInput_throwsException() throws JsonProcessingException {
        mockMapper = mock(ObjectMapper.class);
        transformer = new KafkaMessageTransformer(mockMapper);

        MockEventApiResponse mockEventApiResponse = new MockEventApiResponse();
        when(mockMapper.writeValueAsString(mockEventApiResponse))
                .thenThrow(JsonProcessingException.class);
        assertThrows(RuntimeException.class, () -> transformer.apply(mockEventApiResponse));
    }
}
