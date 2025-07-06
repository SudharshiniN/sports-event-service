package com.sports.event.service;

import com.sports.event.model.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EventServiceTest {

    private EventService eventService;

    @BeforeEach
    public void setUp() {
        eventService = new EventService();
    }

    @Test
    public void testProcessEventAndGetEvents() {
        Event liveEvent = new Event("1", true);
        Event notLiveEvent = new Event("2", false);

        eventService.processEvent(liveEvent);
        eventService.processEvent(notLiveEvent);

        List<Event> liveEvents = eventService.getEvents();

        assertEquals(1, liveEvents.size());
        assertTrue(liveEvents.get(0).getLive());
        assertEquals("1", liveEvents.get(0).getEventId());
    }

    @Test
    public void testRemoveEvent() {
        Event event = new Event("4", true);
        eventService.processEvent(event);

        eventService.removeEvent("4");

        List<Event> liveEvents = eventService.getEvents();
        assertTrue(liveEvents.isEmpty());
    }
}
