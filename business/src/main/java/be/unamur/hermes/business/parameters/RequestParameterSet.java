package be.unamur.hermes.business.parameters;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import be.unamur.hermes.common.enums.ClaimId;

/**
 * This class holds all parameters of all claim types for a given municipality.
 *
 */
public class RequestParameterSet {

    private final String municipality;
    private final Map<ClaimId, RequestType> claims;

    public RequestParameterSet(String municipality) {
	super();
	this.municipality = municipality;
	this.claims = new EnumMap<>(ClaimId.class);
    }

    public String getMunicipality() {
	return municipality;
    }

    public boolean isActivated(ClaimId claimId) {
	RequestType claimType = getClaimType(claimId);
	return claimType == null ? false : claimType.isActivated();
    }

    public RequestType getClaimType(ClaimId claimId) {
	return claims.get(claimId);
    }

    public String getParameter(ClaimId claimId, String parameterId) {
	RequestType claimType = getClaimType(claimId);
	return claimType == null ? null : claimType.getParameter(parameterId);
    }

    public List<RequestType> getClaimTypes() {
	return new ArrayList<>(claims.values());
    }

    @Override
    public String toString() {
	return "ClaimParameterSet [municipality=" + municipality + ", claims=" + claims + "]";
    }

    void addClaimParameters(ClaimId id, RequestType claim) {
	claims.put(id, claim);
    }
}