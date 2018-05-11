package be.unamur.hermes.dataaccess.repository;

import java.util.List;

import be.unamur.hermes.dataaccess.entity.Event;

public interface EventRepository {

    long create(Event event);

    List<Event> getEventsByRequest(long requestID);

    List<Event> getEventsByRequest(long requestID, String eventType);

    List<Event> getEventsByAuthor(long authorId);

    Event getEventById(long id);

}
