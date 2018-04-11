package be.unamur.hermes.business.parameters;

import java.util.HashMap;
import java.util.Map;

import be.unamur.hermes.common.enums.ClaimId;

/**
 * Class represting a type of claim.
 *
 */
public class RequestType {

    private static final String KEY_ACTIVATED = "activated";

    private final ClaimId id;
    private final Map<String, String> parameters = new HashMap<>();

    public RequestType(ClaimId id) {
	super();
	this.id = id;
    }

    @Override
    public String toString() {
	return "ClaimType [id=" + id + ", parameters=" + parameters + "]";
    }

    public boolean isActivated() {
	if (parameters.containsKey(KEY_ACTIVATED)) {
	    String paramObj = parameters.get(KEY_ACTIVATED);
	    return Boolean.parseBoolean(paramObj);
	}
	return false;
    }

    public ClaimId getId() {
	return id;
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