package com.sports.event.externalEvent;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

/**
 * Controller class to act a mock for external api calls
 */
@RestController
public class MockExternalEventApiController {

    private final Random random = new Random();

    @GetMapping("/mock-events/{eventId}")
    public MockEventApiResponse getEventData(@PathVariable String eventId) {
        MockEventApiResponse mockEventApiResponse = new MockEventApiResponse();
        mockEventApiResponse.setEventId(eventId);
        mockEventApiResponse.setCurrentScore(random.nextInt(5) + ":" + random.nextInt(5));
        mockEventApiResponse.setScoreTimeStamp(System.currentTimeMillis());
        return mockEventApiResponse;
    }
}
