package be.unamur.hermes.dataaccess.entity;

import java.util.Map;

/**
 * Class representing a type of request.
 *
 */
public class RequestParameters {

    private final long municipalityId;
    private final long requestTypeId;
    private final Map<String, String> parameters;

    public RequestParameters(long municipalityId, long requestTypeId, Map<String, String> parameters) {
	super();
	this.municipalityId = municipalityId;
	this.requestTypeId = requestTypeId;
	this.parameters = parameters;
    }

    @Override
    public String toString() {
	return "ClaimType [id=" + requestTypeId + ", parameters=" + parameters + "]";
    }

    public long getMunicipalityId() {
	return municipalityId;
    }

    public long getRequestTypeId() {
	return requestTypeId;
    }

    public Map<String, String> getParameters() {
	return parameters;
    }

    public String getParameter(String key) {
	return parameters.get(key);
    }

    /**
     * 
     * @param key
     *            xpath query relative to the root of the claimType.
     * @param value
     */
    void addParameter(String key, String value) {
	parameters.put(key, value);
    }
}