package be.unamur.hermes.business.service;

import java.util.List;

import be.unamur.hermes.dataaccess.entity.Event;

public interface EventService {

    List<Event> findByReq(Long requestID);

    List<Event> findByReq(Long requestID, String eventType);

    List<Event> findByAuthor(Long userAccountId);

    long create(Event event);

    Event findById(long id);

}
