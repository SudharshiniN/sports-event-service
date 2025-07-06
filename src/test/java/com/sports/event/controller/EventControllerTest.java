package com.sports.event.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sports.event.model.Event;
import com.sports.event.service.EventService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EventController.class)
@Import(MockServiceConfig.class)
public class EventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EventService eventService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetEvents() throws Exception {
        Mockito.when(eventService.getEvents()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/events"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    public void testAddEvent() throws Exception {
        Event mockEvent = new Event("1", false);

        mockMvc.perform(post("/events/status")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mockEvent)))
                .andExpect(status().isOk())
                .andExpect(content().string("Event added successfully"));
    }

    @Test
    public void testRemoveEvent() throws Exception {
        mockMvc.perform(delete("/events/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Event removed successfully"));
    }

}
