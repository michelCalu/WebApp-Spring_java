package be.unamur.hermes.dataaccess.entity;

public class RequestType {

    private final long requestTypeId;
    private final String description;

    public RequestType(long requestTypeId, String description) {
	super();
	this.requestTypeId = requestTypeId;
	this.description = description;
    }

    public long getRequestTypeId() {
	return requestTypeId;
    }

    public String getDescription() {
	return description;
    }
}
