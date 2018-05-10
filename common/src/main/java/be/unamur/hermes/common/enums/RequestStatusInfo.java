package be.unamur.hermes.common.enums;

import be.unamur.hermes.common.constants.EventConstants;

public enum RequestStatusInfo {

    CREATED("new"), ONGOING("ongoing"), AWAITING_INFO("awaitingInfo"), REJECTED("rejected"), ACCEPTED("accepted");

    private final String value;

    private RequestStatusInfo(String value) {
	this.value = value;
    }

    public static RequestStatusInfo getStatusFor(String value) {
	for (RequestStatusInfo rsi : values()) {
	    if (rsi.value.equalsIgnoreCase(value))
		return rsi;
	}
	return null;
    }

    public boolean isCitizenInitiated(RequestStatusInfo oldStatus) {
	switch (this) {
	case CREATED:
	    return true;
	case ONGOING: {
	    return RequestStatusInfo.AWAITING_INFO == oldStatus;
	}
	case AWAITING_INFO:
	case ACCEPTED:
	case REJECTED:
	default:
	    return false;
	}
    }

    public String getEventType() {
	switch (this) {
	case ACCEPTED:
	case REJECTED:
	    return EventConstants.TYPE_CLOSED;
	case CREATED:
	    return EventConstants.TYPE_CREATED;
	case AWAITING_INFO:
	case ONGOING:
	default:
	    return EventConstants.TYPE_UPDATED;
	}
    }
}
