package com.sports.event.model;

/**
 * Event object which contains the sports event
 */
public class Event {

    private String eventId;

    private boolean isLive;

    public Event() {
    }

    public Event(String eventId, boolean isLive) {
        this.eventId = eventId;
        this.isLive = isLive;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public Boolean getLive() {
        return isLive;
    }

    public void setLive(Boolean live) {
        isLive = live;
    }
}
