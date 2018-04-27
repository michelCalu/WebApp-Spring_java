package be.unamur.hermes.web.controller;

import be.unamur.hermes.business.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;

public class EventController {

    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }
}
