package be.unamur.hermes.business.service;

import be.unamur.hermes.dataaccess.entity.*;
import be.unamur.hermes.dataaccess.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventServiceImpl implements EventService{

    private EventRepository eventRepository;

    @Autowired
    public EventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }


    @Override
    public Event findByReq(Long requestID) {
        return null;
    }

    @Override
    public Event findByCitizen(Long citizenID) {
        return null;
    }

    @Override
    public Event findByCompany(String entrepriseNb) {
        return null;
    }

    @Override
    public Event findByEmployeen(Long employeeID) {
        return null;
    }
}
