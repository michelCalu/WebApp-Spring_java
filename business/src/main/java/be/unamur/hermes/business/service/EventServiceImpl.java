package be.unamur.hermes.business.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import be.unamur.hermes.dataaccess.entity.Event;
import be.unamur.hermes.dataaccess.repository.EventRepository;

@Service
public class EventServiceImpl implements EventService {

    private EventRepository eventRepository;

    @Autowired
    public EventServiceImpl(EventRepository eventRepository) {
	this.eventRepository = eventRepository;
    }

    @Override
    public List<Event> findByReq(Long requestID) {
	return eventRepository.getEventsByRequest(requestID);
    }

    @Override
    public List<Event> findByAuthor(Long userAccountId) {
	return eventRepository.getEventsByAuthor(userAccountId);
    }

    @Override
    public List<Event> findByReq(Long requestID, String eventType) {
	return eventRepository.getEventsByRequest(requestID, eventType);
    }

    @Override
    public long create(Event event) {
	return eventRepository.create(event);
    }

    @Override
    public Event findById(long id) {
	return eventRepository.getEventById(id);
    }
}
