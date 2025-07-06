package com.sports.event.service;

import com.sports.event.model.Event;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Service to handle the incoming sport events and update in-memory
 */
@Service
public class EventService {

    //variable to hold the events in-memory
    private final Map<String, Event> eventMap = new ConcurrentHashMap<>();


    /**
     * Retrieves the events from memory and filters the live events
     *
     * @return
     */
    public List<Event> getEvents() {
        return eventMap.values()
                .stream()
                .filter(Event::getLive)
                .collect(Collectors.toList());
    }

    /**
     * Adds the incoming event to the memory
     *
     * @param event
     */
    public void processEvent(Event event) {
        eventMap.put(event.getEventId(), event);
    }

    /**
     * Removes the given event from memory
     *
     * @param eventId
     */
    public void removeEvent(String eventId) {
        eventMap.remove(eventId);
    }
}
