package be.unamur.hermes.dataaccess.entity;

import java.time.LocalDateTime;
import java.util.Objects;

public class Event {

    private long id;
    private EventType type;
    private LocalDateTime at;
    private Employee author;
    private Request request;

    public Event() {
    }

    public Event(long id, EventType type, LocalDateTime at, Employee author, Request request) {
        this.id = id;
        this.type = type;
        this.at = at;
        this.author = author;
        this.request = request;
    }

    public long getId() {
        return id;
    }

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    public LocalDateTime getAt() {
        return at;
    }

    public void setAt(LocalDateTime at) {
        this.at = at;
    }

    public Employee getAuthor() {
        return author;
    }

    public void setAuthor(Employee author) {
        this.author = author;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Event)) return false;
        Event event = (Event) o;
        return id == event.id &&
                Objects.equals(type, event.type) &&
                Objects.equals(at, event.at) &&
                Objects.equals(author, event.author) &&
                Objects.equals(request, event.request);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, type, at, author, request);
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", type=" + type +
                ", at=" + at +
                ", author=" + author +
                ", request=" + request +
                '}';
    }
}
