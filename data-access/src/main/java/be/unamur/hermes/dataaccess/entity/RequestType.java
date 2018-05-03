package be.unamur.hermes.dataaccess.entity;

public class RequestType {

    private final long id;
    private final String description;

    public RequestType(long id, String description) {
	super();
	this.id = id;
	this.description = description;
    }

    public long getId() {
	return id;
    }

    public String getDescription() {
	return description;
    }
}
