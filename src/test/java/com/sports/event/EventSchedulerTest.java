package com.sports.event;

import com.sports.event.externalEvent.MockEventApiResponse;
import com.sports.event.externalEvent.MockExternalEventClient;
import com.sports.event.kafka.KafkaMessageTransformer;
import com.sports.event.kafka.KafkaPublisher;
import com.sports.event.model.Event;
import com.sports.event.service.EventService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

public class EventSchedulerTest {

    @InjectMocks
    private EventScheduler eventScheduler;

    @Mock
    private EventService eventService;

    @Mock
    private MockExternalEventClient mockExternalEventClient;

    @Mock
    private KafkaPublisher kafkaPublisher;

    @Mock
    private KafkaMessageTransformer kafkaMessageTransformer;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(eventScheduler, "kafkaTopicName", "test-topic");
    }

    @Test
    public void testPollExternalApi_noEvents() {
        when(eventService.getEvents()).thenReturn(Collections.emptyList());

        eventScheduler.pollExternalApi();

        verify(mockExternalEventClient, never()).invokeExternalEventApi(any());
        verify(kafkaPublisher, never()).publish(any(), any());
    }

    @Test
    public void testPollExternalApi_eventSuccess() {
        Event event = new Event("1", true);
        MockEventApiResponse response = new MockEventApiResponse();
        response.setEventId("1");
        response.setCurrentScore("2:0");
        response.setScoreTimeStamp(System.currentTimeMillis());

        when(eventService.getEvents()).thenReturn(List.of(event));
        when(mockExternalEventClient.invokeExternalEventApi(event)).thenReturn(response);
        when(kafkaMessageTransformer.apply(response)).thenReturn("{\"mock\":\"json\"}");

        eventScheduler.pollExternalApi();

        verify(kafkaPublisher).publish("test-topic", "{\"mock\":\"json\"}");
    }

    @Test
    public void testPollExternalApi_externalApiFails() {
        Event event = new Event("2", true);

        when(eventService.getEvents()).thenReturn(List.of(event));
        when(mockExternalEventClient.invokeExternalEventApi(event)).thenReturn(null); // simulate API failure

        eventScheduler.pollExternalApi();

        verify(kafkaPublisher, never()).publish(any(), any());
    }
}
