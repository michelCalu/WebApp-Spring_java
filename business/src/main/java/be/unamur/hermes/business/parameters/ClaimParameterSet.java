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
public class ClaimParameterSet {

    private final String municipality;
    private final Map<ClaimId, ClaimType> claims;

    public ClaimParameterSet(String municipality) {
	super();
	this.municipality = municipality;
	this.claims = new EnumMap<>(ClaimId.class);
    }

    public String getMunicipality() {
	return municipality;
    }

    public boolean isActivated(ClaimId claimId) {
	ClaimType claimType = getClaimType(claimId);
	return claimType == null ? false : claimType.isActivated();
    }

    public ClaimType getClaimType(ClaimId claimId) {
	return claims.get(claimId);
    }

    public String getParameter(ClaimId claimId, String parameterId) {
	ClaimType claimType = getClaimType(claimId);
	return claimType == null ? null : claimType.getParameter(parameterId);
    }

    public List<ClaimType> getClaimTypes() {
	return new ArrayList<>(claims.values());
    }

    @Override
    public String toString() {
	return "ClaimParameterSet [municipality=" + municipality + ", claims=" + claims + "]";
    }

    void addClaimParameters(ClaimId id, ClaimType claim) {
	claims.put(id, claim);
    }
}