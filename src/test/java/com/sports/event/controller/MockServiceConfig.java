package com.sports.event.controller;

import com.sports.event.service.EventService;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class MockServiceConfig {

    @Bean
    public EventService eventService() {
        return Mockito.mock(EventService.class);
    }
}
