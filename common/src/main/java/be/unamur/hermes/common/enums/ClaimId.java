package be.unamur.hermes.common.enums;

import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ClaimId {

    NATIONALITY_CERTIFICATE("nationalityCertificate"), //
    PARKING_PERMISSION("perm-parking");

    private final String id;

    private ClaimId(String id) {
	this.id = id;
    }

    @JsonValue
    public String getId() {
	return id;
    }

    @JsonCreator
    public static ClaimId getClaimId(String claimIdString) {
	if (!StringUtils.hasText(claimIdString))
	    return null;
	for (ClaimId claimId : ClaimId.values()) {
	    if (claimId.id.equalsIgnoreCase(claimIdString))
		return claimId;
	}
	return null;
    }

}
