package com.sports.event.kafka;

import org.apache.kafka.common.KafkaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Service
public class KafkaPublisher {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private static final Logger logger = LoggerFactory.getLogger(KafkaPublisher.class);

    /**
     * Publish message to kafka topic and in case of error retry
     *
     * @param topic
     * @param message
     */
    @Retryable(
            value = {KafkaException.class},
            maxAttempts = 3,
            backoff = @Backoff(delay = 2000, multiplier = 2)
    )
    public void publish(String topic, String message) {
        kafkaTemplate.send(topic, message);
        logger.info("Message published to kafka {}", message);
    }

    /**
     * Handles retry failures in publishing message to kafka
     *
     * @param message
     * @param exception
     */
    @Recover
    public void handleKafkaFailure(String message, KafkaException exception) {
        logger.error("Failed to publish message after retries {} : error{}" + message, exception.getMessage());
    }
}
