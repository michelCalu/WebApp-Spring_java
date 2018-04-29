package be.unamur.hermes.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum UserStatus {

    CREATED("created"), //
    ACTIVE("active"), //
    DISABLED("disabled");

    private final String value;

    private UserStatus(String value) {
	this.value = value;
    }

    @JsonValue
    public String getValue() {
	return value;
    }

    @JsonCreator
    public static UserStatus getStatus(String status) {
	for (UserStatus us : values()) {
	    if (us.value.equalsIgnoreCase(status))
		return us;
	}
	return null;
    }

}
