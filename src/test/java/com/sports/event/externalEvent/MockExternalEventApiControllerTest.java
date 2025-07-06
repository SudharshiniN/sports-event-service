package com.sports.event.externalEvent;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.isA;
import static org.hamcrest.Matchers.matchesPattern;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MockExternalEventApiController.class)
public class MockExternalEventApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetEventData() throws Exception {
        String testEventId = "match123";

        mockMvc.perform(get("/mock-events/{eventId}", testEventId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.eventId").value(testEventId))
                .andExpect(jsonPath("$.currentScore", matchesPattern("\\d+:\\d+")))
                .andExpect(jsonPath("$.scoreTimeStamp", isA(Number.class)));
    }
}

