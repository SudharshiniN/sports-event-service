package com.sports.event.externalEvent;

/**
 * MockApi object which contains external api response
 */
public class MockEventApiResponse {

    private String eventId;

    private String currentScore;

    private long scoreTimeStamp;

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getCurrentScore() {
        return currentScore;
    }

    public void setCurrentScore(String currentScore) {
        this.currentScore = currentScore;
    }

    public long getScoreTimeStamp() {
        return scoreTimeStamp;
    }

    public void setScoreTimeStamp(long scoreTimeStamp) {
        this.scoreTimeStamp = scoreTimeStamp;
    }
}
