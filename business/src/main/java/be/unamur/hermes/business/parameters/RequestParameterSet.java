package be.unamur.hermes.business.parameters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class holds all parameters of all claim types for a given municipality.
 *
 */
public class RequestParameterSet {

    private final String municipality;
    private final Map<String, RequestParameters> claims;

    public RequestParameterSet(String municipality) {
	super();
	this.municipality = municipality;
	this.claims = new HashMap<>();
    }

    public String getMunicipality() {
	return municipality;
    }

    public boolean isActivated(String requestTypeId) {
	RequestParameters claimType = getClaimType(requestTypeId);
	return claimType == null ? false : claimType.isActivated();
    }

    public RequestParameters getClaimType(String requestTypeId) {
	return claims.get(requestTypeId);
    }

    public String getParameter(String requestTypeId, String parameterId) {
	RequestParameters claimType = getClaimType(requestTypeId);
	return claimType == null ? null : claimType.getParameter(parameterId);
    }

    public List<RequestParameters> getClaimTypes() {
	return new ArrayList<>(claims.values());
    }

    @Override
    public String toString() {
	return "ClaimParameterSet [municipality=" + municipality + ", claims=" + claims + "]";
    }

    void addClaimParameters(String requestTypeId, RequestParameters claim) {
	claims.put(requestTypeId, claim);
    }
}