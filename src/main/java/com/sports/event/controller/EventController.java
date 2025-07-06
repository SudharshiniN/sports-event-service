package com.sports.event.controller;

import com.sports.event.model.Event;
import com.sports.event.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Event Controller class which includes endpoints for handling sport events.
 */
@RestController
@RequestMapping("/events")
public class EventController {

    @Autowired
    private EventService eventService;

    /**
     * Retrieves the available events from in-memory
     *
     * @return
     */
    @GetMapping
    public List<Event> getEvents() {
        return eventService.getEvents();
    }

    /**
     * Adds the incoming event details to in-memory store
     *
     * @param event
     * @return
     */
    @PostMapping("/status")
    public ResponseEntity<String> addEvent(@RequestBody Event event) {
        eventService.processEvent(event);
        return ResponseEntity.ok("Event added successfully");
    }

    /**
     * Remove the given event in-memory
     *
     * @param eventId
     * @return
     */
    @DeleteMapping("/{eventId}")
    public ResponseEntity<String> removeEvent(@PathVariable String eventId) {
        eventService.removeEvent(eventId);
        return ResponseEntity.ok("Event removed successfully");
    }

}

