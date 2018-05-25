package be.unamur.hermes.dataaccess.entity;

import java.time.LocalDateTime;

public class Event {

    private long id;
    private EventType type;
    private LocalDateTime at;
    private long authorId;
    private long requestId;
    private String comment;

    public static Event create(String eventType, long authorId, long requestId) {
	return create(eventType, authorId, requestId, null);
    }

    public static Event create(String eventType, long authorId, long requestId, String comment) {
	return new Event(0L, new EventType(0L, eventType), LocalDateTime.now(), authorId, requestId, comment);
    }

    public Event() {
    }

    public Event(long id, EventType type, LocalDateTime at, long authorId, long requestId) {
	this.id = id;
	this.type = type;
	this.at = at;
	this.authorId = authorId;
	this.requestId = requestId;
    }

    public Event(long id, EventType type, LocalDateTime at, long authorId, long requestId, String comment) {
	this.id = id;
	this.type = type;
	this.at = at;
	this.authorId = authorId;
	this.requestId = requestId;
	this.comment = comment;
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

    public long getAuthorId() {
	return authorId;
    }

    public void setAuthorId(long authorId) {
	this.authorId = authorId;
    }

    public long getRequestId() {
	return requestId;
    }

    public void setRequestId(long requestId) {
	this.requestId = requestId;
    }

    public String getComment() {
	return comment;
    }

    public void setComment(String comment) {
	this.comment = comment;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((at == null) ? 0 : at.hashCode());
	result = prime * result + (int) (authorId ^ (authorId >>> 32));
	result = prime * result + (int) (id ^ (id >>> 32));
	result = prime * result + (int) (requestId ^ (requestId >>> 32));
	result = prime * result + ((type == null) ? 0 : type.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	Event other = (Event) obj;
	if (at == null) {
	    if (other.at != null)
		return false;
	} else if (!at.equals(other.at))
	    return false;
	if (authorId != other.authorId)
	    return false;
	if (id != other.id)
	    return false;
	if (requestId != other.requestId)
	    return false;
	if (type == null) {
	    if (other.type != null)
		return false;
	} else if (!type.equals(other.type))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return "Event [id=" + id + ", type=" + type + ", at=" + at + ", authorId=" + authorId + ", requestId="
		+ requestId + "]";
    }
}
