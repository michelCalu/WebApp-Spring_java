package be.unamur.hermes.dataaccess.entity;

import java.util.Objects;

public class EventType {

    private long id;
    private String description;

    public EventType(){}

    public EventType(long id, String description) {
        this.id = id;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EventType)) return false;
        EventType eventType = (EventType) o;
        return id == eventType.id &&
                Objects.equals(description, eventType.description);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, description);
    }

    @Override
    public String toString() {
        return "EventType{" +
                "id=" + id +
                ", description='" + description + '\'' +
                '}';
    }
}
