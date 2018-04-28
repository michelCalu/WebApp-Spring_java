package be.unamur.hermes.business.parameters;

import java.util.HashMap;
import java.util.Map;

/**
 * Class represting a type of claim.
 *
 */
public class RequestParameters {

    private static final String KEY_ACTIVATED = "activated";

    private final String requestTypeId;
    private final Map<String, String> parameters = new HashMap<>();

    public RequestParameters(String requestTypeId) {
	super();
	this.requestTypeId = requestTypeId;
    }

    @Override
    public String toString() {
	return "ClaimType [id=" + requestTypeId + ", parameters=" + parameters + "]";
    }

    public boolean isActivated() {
	if (parameters.containsKey(KEY_ACTIVATED)) {
	    String paramObj = parameters.get(KEY_ACTIVATED);
	    return Boolean.parseBoolean(paramObj);
	}
	return false;
    }

    public String getId() {
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