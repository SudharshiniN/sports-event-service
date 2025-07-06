package com.sports.event;

import com.sports.event.externalEvent.MockEventApiResponse;
import com.sports.event.externalEvent.MockExternalEventClient;
import com.sports.event.kafka.KafkaMessageTransformer;
import com.sports.event.kafka.KafkaPublisher;
import com.sports.event.model.Event;
import com.sports.event.service.EventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * EventScheduler is a scheduled task that runs every n (configurable) seconds to poll an external
 * sports event API for each live event in memory.
 * <p>
 * The data is transformed into a simple message format and published to a Kafka topic.
 * This class helps keep live event updates in near real-time.
 * <p>
 * Dependencies:
 * - eventService: provides active events
 * - kafkaPublisher: handles Kafka publishing
 * - restTemplate: fetches data from external API
 * - kafkaMessageTransformer: transforms the external API response to kafka message
 */

@Component
public class EventScheduler {

    @Autowired
    private EventService eventService;

    @Autowired
    private MockExternalEventClient mockExternalEventClient;

    @Autowired
    private KafkaPublisher kafkaPublisher;

    @Autowired
    private KafkaMessageTransformer kafkaMessageTransformer;

    @Value("${kafka.topic.name}")
    private String kafkaTopicName;

    private static final Logger logger = LoggerFactory.getLogger(EventScheduler.class);

    /**
     * Periodically polls the external API for each live event and publishes the
     * response as a message to Kafka. Scheduled to run every given n (configurable) seconds.
     */
    @Scheduled(fixedRateString = "${polling.interval-ms}")
    public void pollExternalApi() {
        if (eventService.getEvents().isEmpty()) {
            logger.info("No events to process");
        }

        for (Event event : eventService.getEvents()) {
            MockEventApiResponse mockEventApiResponse = mockExternalEventClient.invokeExternalEventApi(event);
            if (null != mockEventApiResponse) {
                String message = kafkaMessageTransformer.apply(mockEventApiResponse);
                kafkaPublisher.publish(kafkaTopicName, message);
            } else {
                logger.error("Failed external api call and skipping eventid-{} not publishing to kafka", event.getEventId());
            }
        }
    }


}
