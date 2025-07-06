package com.sports.event.externalEvent;

import com.sports.event.model.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MockExternalEventClientTest {

    private MockExternalEventClient mockExternalEventClient;

    @Mock
    private RestTemplate restTemplate;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockExternalEventClient = new MockExternalEventClient();
        // Inject mock fields
        ReflectionTestUtils.setField(mockExternalEventClient, "restTemplate", restTemplate);
        ReflectionTestUtils.setField(mockExternalEventClient, "mockApiUrl", "http://localhost:8080/mock-events");
    }

    @Test
    public void testInvokeExternalEventApi_Success() {
        Event event = new Event("123", false);
        MockEventApiResponse mockResponse = new MockEventApiResponse();
        mockResponse.setEventId("123");
        mockResponse.setCurrentScore("2:1");
        mockResponse.setScoreTimeStamp(System.currentTimeMillis());

        when(restTemplate.getForEntity(
                eq("http://localhost:8080/mock-events/123"),
                eq(MockEventApiResponse.class)))
                .thenReturn(ResponseEntity.ok(mockResponse));

        MockEventApiResponse result = mockExternalEventClient.invokeExternalEventApi(event);

        assertNotNull(result);
        assertEquals("123", result.getEventId());
        assertEquals("2:1", result.getCurrentScore());
    }

    @Test
    public void testInvokeExternalEventApi_Failure() {
        Event event = new Event("fail", false);

        when(restTemplate.getForEntity(
                anyString(),
                eq(MockEventApiResponse.class)))
                .thenThrow(new RestClientException("API error"));

        MockEventApiResponse result = mockExternalEventClient.invokeExternalEventApi(event);

        assertNull(result);
    }
}

