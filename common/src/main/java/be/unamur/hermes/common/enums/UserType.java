package be.unamur.hermes.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum UserType {

    CITIZEN("ctz"), EMPLOYEE("empl");

    private final String value;

    private UserType(String value) {
	this.value = value;
    }

    @JsonValue
    public String getValue() {
	return value;
    }

    @JsonCreator
    public static UserType getStatus(String status) {
	for (UserType type : values()) {
	    if (type.value.equalsIgnoreCase(status))
		return type;
	}
	return null;
    }
}
