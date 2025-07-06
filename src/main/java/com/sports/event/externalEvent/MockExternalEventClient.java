package com.sports.event.externalEvent;

import com.sports.event.model.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * MockExternalEventClient invokes the external api for the given eventid
 */
@Component
public class MockExternalEventClient {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${mock.api.base-url}")
    private String mockApiUrl;

    private static final Logger logger = LoggerFactory.getLogger(MockExternalEventClient.class);

    /**
     * Invokes External api for the given eventid
     *
     * @param event
     * @return
     */
    public MockEventApiResponse invokeExternalEventApi(Event event) {
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        logger.info("Polling external api for eventid-{} at {}", event.getEventId(), time);

        String url = mockApiUrl + "/" + event.getEventId();
        try {
            ResponseEntity<MockEventApiResponse> mockApiResponseResponseEntity = restTemplate.getForEntity(url, MockEventApiResponse.class);
            return mockApiResponseResponseEntity.getBody();
        } catch (RestClientException e) {
            logger.error("Error polling external api for eventid-{} : error {}", event.getEventId(), e.getMessage());
        }
        return null;
    }
}
