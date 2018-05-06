package be.unamur.hermes.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum MandataryRole {

    OWNER("owner"), MANAGER("manager"), READER("reader");

    private final String value;

    private MandataryRole(String value) {
	this.value = value;
    }

    @JsonValue
    public String getValue() {
	return value;
    }

    @JsonCreator
    public static MandataryRole getRole(String role) {
	for (MandataryRole mr : values()) {
	    if (mr.value.equalsIgnoreCase(role))
		return mr;
	}
	return null;
    }
}
