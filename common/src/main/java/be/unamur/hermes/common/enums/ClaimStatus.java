package be.unamur.hermes.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * This type enumerates the different statuses a claim can be in.
 *
 */
public enum ClaimStatus {

    /**
     * the claim has not yet been assigned
     */
    NEW,
    /**
     * the claim has been assigned
     */
    WIP,
    /**
     * the claim has been returned to the requestor
     */
    AWAITING_INFO,
    /**
     * claim accepted
     */
    DONE,
    /**
     * claim rejected
     */
    REJECTED;

    @JsonValue
    public int getId() {
	return this.ordinal();
    }

    @JsonCreator
    public static ClaimStatus getStatus(int statusId) {
	try {
	    return values()[statusId];
	} catch (ArrayIndexOutOfBoundsException e) {
	    return null;
	}
    }
}
